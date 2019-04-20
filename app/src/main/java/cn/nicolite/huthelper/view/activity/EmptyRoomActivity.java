package cn.nicolite.huthelper.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.EmptyRoom;
import cn.nicolite.huthelper.presenter.EmptyRoomPreseter;
import cn.nicolite.huthelper.utils.DateUtils;
import cn.nicolite.huthelper.utils.MyDialog;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.EmptyClassAdater;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.customView.LoadingDialog;
import cn.nicolite.huthelper.view.iview.IEmptyRoomView;

public class EmptyRoomActivity extends BaseActivity<IBaseView,BaseActivity> implements IEmptyRoomView {

    @BindView(R.id.bt_serach)
    Button bt_serach;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.toolbar_menu)
    ImageView ima_menu;
    @BindView(R.id.tv_timeticke)
    TextView tv_timeticke;
    @BindView(R.id.toolbar_back)
    ImageView imageView;
    @BindView(R.id.toolbar_title)
    TextView textView;
    @BindView(R.id.rv_emptyclasslist)
    RecyclerView recyclerView;
    @BindView(R.id.spi_time)    //时间
    Spinner spinner_time;
    @BindView(R.id.spi_name)    //教学楼名称
    Spinner spinner_name;
    static String[] roomName1 = {"理学楼","老冶金楼","包设","包印","电气", //0 - 4
                            "法学楼","公共", "机械楼","计通", "经贸",//5 - 9
                            "商学", "思政楼", "体育","土木","外语楼",// 10 - 14
                            "文新", "冶金","音乐","综合","河东电教楼",//15 - 19
                            "河东体育馆","河东实验楼","河东主教楼" //20 - 22
                        };
    private static String[] time_inday = {"全天","上午","下午","晚上"};
    private static String roomId = "04";
    private static String time = "am";
    private static EmptyRoomPreseter emptyRoomPreseter;
    private EmptyClassAdater emptyClassAdater;
    private List<EmptyRoom.Data.JsList> list = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private String pwd;

    private static String date = DateUtils.getDateFormat("yyyy-MM-dd");//日期
    @Override
    protected int setLayoutId() {
        return R.layout.activity_empty_room;
    }



    @Override
    protected void doBusiness() {

            tv_date.setText(date);
            textView.setText("空教室查询");
            spinner_name.setAdapter(new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,roomName1));
            spinner_name.setSelection(6);
            spinner_name.setOnItemSelectedListener(new RoomListener());
            spinner_time.setAdapter(new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,time_inday));
            spinner_time.setSelection(1);
            spinner_time.setOnItemSelectedListener(new TimeListener());
            recyclerView.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
            emptyClassAdater = new EmptyClassAdater(this,list);
            recyclerView.setAdapter(emptyClassAdater);
            emptyRoomPreseter = new EmptyRoomPreseter(this, this);
            //登录获取token
            if (getSharedPreferences(Constants.USER_XML,MODE_PRIVATE).getBoolean("is_first_input",true)){
                SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_XML,MODE_PRIVATE).edit();
                editor.putBoolean("is_first_input",false);
                editor.apply();
                showDialog();
                pwd = getSharedPreferences(Constants.USER_XML,MODE_PRIVATE).getString(Constants.USER_PWD,"");
                emptyRoomPreseter.getUserToken(getConfigureList().get(0).getStudentKH(),pwd);
                //查询空教室
                emptyRoomPreseter.showEmptyRoom(date,"allday","04","");
            }else {
                //查询空教室
                emptyRoomPreseter.showEmptyRoom(date,"allday","04","");
            }

    }

    @OnClick({R.id.toolbar_back,R.id.toolbar_menu,R.id.tv_timeticke,R.id.tv_date,R.id.bt_serach})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_menu:
                showDialog();
                break;
            case R.id.tv_date:
            case R.id.tv_timeticke:
                selectTime();
                break;
            case R.id.bt_serach:
                emptyRoomPreseter.showEmptyRoom(date,time,roomId,"");
                break;

        }
    }

    //时间选择
    private void selectTime() {

        Calendar  calendar = Calendar.getInstance();
         final DatePickerDialog datePickerDialog = new DatePickerDialog(context, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String str_m;
                        String str_d;
                        if (month<10){
                            str_m = "0"+(month+1);
                        }else {
                            str_m = ""+month;
                        }
                        if (dayOfMonth<10){
                            str_d = "0"+dayOfMonth;
                        }else {
                            str_d = ""+dayOfMonth;
                        }
                        date = year+"-"+(str_m)+"-"+str_d;
                        tv_date.setText(date);

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
         datePickerDialog.show();


    }

    @Override
    public void showEmptyRoom(List<EmptyRoom.Data.JsList> list) {
         this.list.clear();
         this.list.addAll(list);
         emptyClassAdater.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
            if (loadingDialog == null){
                loadingDialog = new LoadingDialog(this);
                loadingDialog.setLoadingText("正在加载数据");
            }
            loadingDialog.show();
    }

    @Override
    public void closeLoading() {
            if (loadingDialog !=null){
                loadingDialog.dismiss();
            }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.showToastLong(msg);
    }



    class TimeListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        time = "allday";
                    break;
                    case 1:
                        time = "am";
                        break;
                    case 2:
                        time = "pm";
                        break;
                    case 3:
                        time = "night";
                        break;
                }
            emptyRoomPreseter.showEmptyRoom(date,time,roomId,"");

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private static class RoomListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    //理学楼
                    roomId = "14";
                    break;
                case 1:
                    //老冶金楼
                    roomId = "21";
                    break;
                case 2:
                    //包设
                    roomId = "08";
                    break;
                case 3:
                    //包印楼
                    roomId = "16";
                    break;
                case 4:
                    //电气
                    roomId = "18";
                    break;
                case 5:
                    //法学
                    roomId = "24";
                    break;
                case 6:
                    //公共
                    roomId = "04";
                    break;
                case 7:
                    //机械
                    roomId = "25";
                    break;
                case 8:
                    // 计通
                    roomId = "19";
                    break;
                case 9:
                    //经贸
                    roomId = "09";
                    break;
                case 10:
                    //商学
                    roomId = "07";
                    break;
                case 11:
                    //思政
                    roomId = "11";
                    break;
                case 12:
                    //体育
                    roomId = "17";
                    break;
                case 13:
                    //土木
                    roomId = "26";
                    break;
                case 14:
                    //外语
                    roomId = "05";
                    break;
                case 15:
                    //文新
                    roomId = "23";
                    break;
                case 16:
                    //冶金
                    roomId = "22";
                    break;
                case 17:
                    //音乐
                    roomId = "20";
                    break;
                case 18:
                    //综合
                    roomId = "01";
                    break;
                case 19:
                    //河东电教楼
                    roomId = "10";
                    break;
                case 20:
                    //河东体育馆
                    roomId = "12";
                    break;
                case 21:
                    //河东实验楼
                    roomId = "06";
                    break;
                case 22:
                    //河东主教楼
                    roomId = "03";
                    break;
                    default:
                        roomId = "04";
            }
            emptyRoomPreseter.showEmptyRoom(date,time,roomId,"");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void showDialog(){
        final MyDialog myDialog = new MyDialog(this);

        try{
            myDialog.setXH(getSharedPreferences(Constants.USER_XML,MODE_PRIVATE).getString(Constants.USER_QZZH,getConfigureList().get(0).getStudentKH()));
            myDialog.setPass(getSharedPreferences(Constants.USER_XML,MODE_PRIVATE).getString(Constants.USER_PWD,""));
        }catch (Exception e){
            e.printStackTrace();
        }
        myDialog.setTitle("请输入教务系统账号密码：")
                .setPositiveButton("确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //存储密码
                        SharedPreferences.Editor token_editer = getSharedPreferences(Constants.USER_XML,Context.MODE_PRIVATE).edit();
                        token_editer.putString(Constants.USER_PWD,myDialog.getPassword());
                        token_editer.putString(Constants.USER_QZZH,myDialog.getXh());
                        token_editer.apply();
                        emptyRoomPreseter.getUserToken(myDialog.getXh(),myDialog.getPassword());
                        myDialog.dismiss();
                    }
                })
                .show();
    }
}
