package cn.nicolite.huthelper.view.activity;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.db.dao.LessonDao;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.ClassTimeTable;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.model.bean.Lesson;
import cn.nicolite.huthelper.network.api.AllClasstimeAPI;
import cn.nicolite.huthelper.presenter.PreseterSpear;
import cn.nicolite.huthelper.utils.DateUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.ScreenUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.ChooseListAdapter;
import cn.nicolite.huthelper.view.customView.LoadingDialog;
import cn.nicolite.huthelper.view.fragment.SpareFragment;
import cn.nicolite.huthelper.view.iview.ISyllabusView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpareActivity extends BaseActivity<IBaseView, BaseActivity> implements ISyllabusView {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rootViewx)
    FrameLayout frameLayout;
    @BindView(R.id.contentx)
    FrameLayout frameLayoutcontent;
    SpareFragment spareFragment;
    PreseterSpear preseterSpear;
    int CurrWeek = 0;
    int chooseNum;
    private LoadingDialog loadingDialog;

    private List<Lesson> lessonList = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapterCollege;
    static ArrayAdapter<String> classarrayAdapter;
    @BindView(R.id.spinner_college)
    Spinner spinner_college;
    @BindView(R.id.spinner_class)
    Spinner spinner_class;
    Configure configure;
    String classnamesss = "交通控制1801";
    private static ClassTimeTable classTimeTable;
    static String college;
    String[] colleget = {"交通工程学院", "体育学院","冶金与材料工程学院","包装与材料工程学院","包装设计艺术学院", "商学院", "国际学院",   "土木工程学院", "外国语学院", "城市与环境学院", "文学与新闻传播学院", "机械工程学院",   "法学院", "理学院", "生命科学与化学学院",  "电气与信息工程学院", "经济与贸易学院", "计算机学院","醴陵陶瓷学院", "音乐学院", "马克思主义学院"};
     static List<String> classlist = new ArrayList<>();

    public static final String SELECT_CLASS = "select_class";
    public static final String SELECT  = "select_college";
    public static final String SELECT_COLLEGE_POSITION = "selectCollegePosition";
    public static final String SELECT_CLASS_POSITION = "selectClassPosition";

    private final MyHandler1 mHandler = new  MyHandler1(this);

    private static class MyHandler1 extends Handler {
        private final WeakReference<SpareActivity> mActivity;
        public MyHandler1(SpareActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage( Message msg) {
            SpareActivity mainActivity = mActivity.get();
            if (mainActivity == null) {
                return;
            }
            classTimeTable = (ClassTimeTable)msg.obj;
            try {
                if (classTimeTable != null) {
                    classlist.clear();
                    classlist.addAll(classTimeTable.getData().getList(college));
                    classarrayAdapter.notifyDataSetChanged();
                } else {
                     ToastUtils.showToastLong("发生异常 请重试！");
                }
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showToastLong("发生异常 请重试！");
            }

        }
    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {
            classlist.add("交通控制1801");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_spearlayout;
    }

    @Override
    protected void doBusiness() {
        userId = getLoginUser();
        if (TextUtils.isEmpty(userId)) {
            startActivity(LoginActivity.class);
            finish();
        }
        List<Configure> configureList = getConfigureList();
        if (ListUtils.isEmpty(configureList)) {
            startActivity(LoginActivity.class);
            finish();
        }
        configure = configureList.get(0);

        try{
            getCollegeClass();//提前请求回资源
            arrayAdapterCollege = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colleget);
            classarrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classlist);
            spinner_college.setAdapter(arrayAdapterCollege);
            spinner_class.setAdapter(classarrayAdapter);

            SharedPreferences preferences =  getSharedPreferences(SELECT,Context.MODE_PRIVATE);
            int collegeposition = preferences.getInt(SELECT_COLLEGE_POSITION,0);
            spinner_college.setSelection(collegeposition);
//
//            SharedPreferences preferences2 =  getSharedPreferences(colleget[collegeposition],Context.MODE_PRIVATE);
//            int classposition = preferences2.getInt(SELECT_CLASS_POSITION,0);
//            spinner_class.setSelection(classposition);


            spinner_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SELECT,Context.MODE_PRIVATE).edit();
                    editor.putInt(SELECT_COLLEGE_POSITION,position);
                    editor.apply();
                    college = colleget[position];

                    try{
                        if (classTimeTable != null) {
                            classlist.clear();
                            classlist.addAll(classTimeTable.getData().getList(college));
                            classarrayAdapter.notifyDataSetChanged();
                        } else {
                            Log.i("class", "classTimeTable is null");
                        }
                    }catch (Exception e){
                        ToastUtils.showToastLong("发生异常请重试");
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




            spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences preferences =  getSharedPreferences(SELECT,Context.MODE_PRIVATE);
                    int lastCollege = preferences.getInt(SELECT_COLLEGE_POSITION,0);


                    SharedPreferences.Editor editor_class = getApplicationContext().getSharedPreferences(colleget[lastCollege],Context.MODE_PRIVATE).edit();
                    editor_class.putInt(SELECT_CLASS_POSITION,position);
                    editor_class.apply();

                    classnamesss = classlist.get(position);
                    preseterSpear.showAllSyllabus(true,classnamesss);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        if (ListUtils.isEmpty(getConfigureList()) || TextUtils.isEmpty(userId) || userId.equals("*")) {
            startActivity(SplashActivity.class);
            finish();
        } else {
            CurrWeek = DateUtils.getNowWeek();
            toolbarTitle.setText(String.valueOf("第" + CurrWeek + "周"));

            chooseNum = CurrWeek - 1;

            SharedPreferences.Editor edit = getSharedPreferences("choose", MODE_PRIVATE).edit();
            edit.putInt("position", chooseNum);
            edit.apply();

            lessonList.addAll(daoSession.getLessonDao()
                    .queryBuilder()
                    .where(LessonDao.Properties.UserId.eq(Constants.USER_ID))
                    .list());

            spareFragment = SpareFragment.newInstance();
           getSupportFragmentManager().beginTransaction().replace(R.id.contentx, spareFragment).commit();

            preseterSpear = new PreseterSpear(this,this);

            preseterSpear.showAllSyllabus(false,classnamesss);
        }
    }

    @Override
    public void showSyllabus(List<Lesson> lessonList) {

    }

    @Override
    public void showAllClassSyllabus(List<Lesson> lessonList) {
        this.lessonList.clear();
        this.lessonList.addAll(lessonList);

        CurrWeek = DateUtils.getNowWeek();
        toolbarTitle.setText(String.valueOf("第" + CurrWeek + "周(本周)"));
        chooseNum = CurrWeek - 1;
        SharedPreferences.Editor edit = getSharedPreferences("choose", MODE_PRIVATE).edit();
        edit.putInt("position", chooseNum);
        edit.apply();

        frameLayoutcontent.setVisibility(View.VISIBLE);

        if (spareFragment != null) {
            spareFragment.updateData();
        }
        setResult(Constants.REFRESH);
    }

    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog(context)
                .setLoadingText("加载中...");
        loadingDialog.show();
    }

    @Override
    public void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        SnackbarUtils.showShortSnackbar(frameLayout, msg);
    }



    //请求数据
    public void getCollegeClass(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AllClasstimeAPI allClasstimeAPI = retrofit.create(AllClasstimeAPI.class);
        Call<ClassTimeTable> call = allClasstimeAPI.getClassSyllabus(configure.getStudentKH(),configure.getAppRememberCode());
        call.enqueue(new Callback<ClassTimeTable>() {
            @Override
            public void onResponse(Call<ClassTimeTable> call, final Response<ClassTimeTable> response) {
                Message message  = new Message();
                message.obj = response.body();
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<ClassTimeTable> call, Throwable t) {
                t.printStackTrace();
                mHandler.sendEmptyMessage(0x401);//待处理网络请求失败
            }
        });
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_title, R.id.toolbar_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_title:
                showWeekListWindows(toolbarTitle);
                break;
            case R.id.toolbar_refresh:
                preseterSpear.showAllSyllabus(true,classnamesss);
                spareFragment.fillDate();
                spareFragment.updateData();
                break;
        }
    }


    /**
     * 选择周数弹出窗口
     */
    protected PopupWindow weekListWindow;
    /**
     * 显示周数的listview
     */
    protected ListView weekListView;

    private void showWeekListWindows(View parent) {

        if (ListUtils.isEmpty(lessonList)) {
            showMessage("暂未导入课表");
            return;
        }

        int width = ScreenUtils.getScreenWidth(context) / 2;
        if (weekListWindow == null) {
            View popupWindowLayout = LayoutInflater.from(context).inflate(R.layout.popupwindow_coursetable,frameLayout, false);
            weekListView = (ListView) popupWindowLayout.findViewById(R.id.lv_weekchoose_coursetable);
            final List<String> weekList = new ArrayList<>();
            for (int i = 1; i <= 25; i++) {
                if (i == CurrWeek) {
                    weekList.add("第" + i + "周(本周)");
                } else
                    weekList.add("第" + i + "周");
            }

            weekListView.setAdapter(new ChooseListAdapter(context, weekList));
            weekListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    weekListWindow.dismiss();
                    SharedPreferences.Editor edit = getSharedPreferences("choose", MODE_PRIVATE).edit();
                    edit.putInt("position", position);
                    edit.apply();
                    toolbarTitle.setText(weekList.get(position));
                    chooseNum = position;
                    spareFragment.changeWeek(position + 1, DateUtils.getNextSunday(DateUtils.addDate(new Date(), (position + 1 - CurrWeek) * 7)));
                }
            });
            weekListWindow = new PopupWindow(popupWindowLayout, width, width + 100);
        }

        weekListView.post(new Runnable() {
            @Override
            public void run() {
                weekListView.smoothScrollToPosition(chooseNum);
            }
        });

        frameLayout.setForeground(getResources().getDrawable(R.color.bg_black_shadow));

        weekListWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                frameLayout.setForeground(getResources().getDrawable(R.color.transparent));
            }
        });

        weekListWindow.setFocusable(true);
        weekListWindow.setOutsideTouchable(true);//设置点击外部可消失
        weekListWindow.setBackgroundDrawable(new BitmapDrawable());
        weekListWindow.showAsDropDown(parent, -(width - parent.getWidth()) / 2, 20);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST) {
            if (spareFragment != null) {
                spareFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 选择班级位置
     */
    public void  selectPosition(){
        SharedPreferences sharedPreferences = getSharedPreferences(SELECT_CLASS,Context.MODE_PRIVATE);
        try{
            if (sharedPreferences!=null){
                int position = sharedPreferences.getInt(SELECT_CLASS_POSITION,0);
                try {
                    spinner_class.setSelection(position);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}