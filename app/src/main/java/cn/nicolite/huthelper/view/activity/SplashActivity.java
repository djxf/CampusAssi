package cn.nicolite.huthelper.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.utils.ListUtils;
/**
 * 闪屏页
 * Created by nicolite on 17-10-17.
 */
public class SplashActivity extends BaseActivity<IBaseView, BaseActivity> implements SplashADListener {

    public String TAG="GDT";
    private static final int what = 958;
    private static final int what2 = 968;
    MyHandler myHandler = new MyHandler(this);
    private ViewGroup viewGroup;
    private ImageView splashHolder;
    private TextView textView;

    static class MyHandler extends Handler {
        WeakReference<SplashActivity> splashActivityWeakReference;
        public MyHandler(SplashActivity splashActivity) {
            splashActivityWeakReference= new WeakReference<SplashActivity>(splashActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity2 = splashActivityWeakReference.get();
            switch (msg.what) {
                case what:
                    splashActivity2.startActivity(LoginActivity.class);
                    splashActivity2.finish();
                    break;
                case what2:
                    splashActivity2.startActivity(MainActivity.class);
            }
        }
    }

    @Override
    protected void doBusiness() {
        viewGroup =  findViewById(R.id.splashContainer);
        splashHolder = this.findViewById(R.id.splashHolder);
        textView = findViewById(R.id.skipView);
        if (!isLogin()){
           myHandler.sendEmptyMessageDelayed(what,300);
        }else {
            // 如果targetSDKVersion >= 23，就要申请好权限。如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
            if (Build.VERSION.SDK_INT >= 23) {
                checkAndRequestPermission();
            } else {
                // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
                fetchSplashAD(SplashActivity.this,viewGroup,textView,Constants.GDT_APPID, Constants.GDT_POSID,this,3000);
            }
        }
    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }



    //djxf:判断是否登录
    public boolean isLogin() {
        String userId=getLoginUser();
        //对1.3.8以前的版本做兼容，需要重新登录, 版本迭代完成后可删除
        List<Configure> configureList = getConfigureList();
        if (!ListUtils.isEmpty(configureList)) {
            if (TextUtils.isEmpty(configureList.get(0).getStudentKH())) {
                return false;
            }
        }
        return userId != null && !userId.equals("*") && !ListUtils.isEmpty(getConfigureList());
    }

    /**
     * 广点通监听
     */
    @Override
    public void onADDismissed() {
        Log.i(TAG,"dissmiss");
        startActivity(MainActivity.class);
        this.finish();
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.i(TAG,adError.getErrorMsg()+adError.getErrorMsg());
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onADPresent() {
        splashHolder.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
        Log.i(TAG, "SplashADPresent");
    }

    @Override
    public void onADClicked() {
           
    }

    @Override
    public void onADTick(long l){
        // 5 4 3 2 1 0
        Log.i("AD_DEMO", "SplashADTick " + l + "ms");
        if (Math.round(l / 1000f) >=4 ){
            textView.setText(String.format("跳过"+3));
        }else if (Math.round(l / 1000f) >=2){
            textView.setText(String.format("跳过"+2));
        }else {
            textView.setText(String.format("跳过"+1));
        }
    }

    @Override
    public void onADExposure() {
        Log.i(TAG,"SplashADExTAGposure");
    }

    private void fetchSplashAD(Activity activity, ViewGroup adContainer,View skipView ,String appId, String posId, SplashADListener adListener, int fetchDelay) {
        SplashAD splashAD = new SplashAD(activity, adContainer,skipView, appId, posId, adListener, fetchDelay);
    }

    /**
     * 动态权限申请
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }



        //权限处理完毕 开始展示广告
        if (lackedPermission.size() == 0) {
            fetchSplashAD(SplashActivity.this,viewGroup,textView,Constants.GDT_APPID, Constants.GDT_POSID,this,3000);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            fetchSplashAD(SplashActivity.this,viewGroup,textView,Constants.GDT_APPID, Constants.GDT_POSID,this,3000);
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }
}
