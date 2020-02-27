package cn.nicolite.huthelper.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.stat.StatService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.nicolite.huthelper.db.DaoHelper;
import cn.nicolite.huthelper.db.DaoUtils;
import cn.nicolite.huthelper.db.dao.ConfigureDao;
import cn.nicolite.huthelper.db.dao.DaoSession;
import cn.nicolite.huthelper.listener.ActivityLifeCycleListener;
import cn.nicolite.huthelper.manager.ActivityStackManager;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.SlidrUtils;
import cn.nicolite.huthelper.utils.StatusBarUtils;

/**
 * Activity 基类 包含生命周期管理
 * 所有Activity都要继承此类
 * Created by nicolite on 17-9-6.
 */

public abstract class BaseActivity<I extends IBaseView, A extends BaseActivity> extends RxAppCompatActivity {
    /**
     * Log Tag
     */
    protected final String TAG = getClass().getSimpleName();
    protected Context context;
    protected Activity activity;
    protected ActivityLifeCycleListener lifeCycleListener;
    protected Unbinder unbinder;
    protected static final int SENSOR = 697;
    protected static final int PORTRAIT = 519;
    protected static final int LANDSCAPE = 539;
    protected DaoSession daoSession;
    protected String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, TAG + "-->onCreate()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onCreate(savedInstanceState);
        }
        ActivityStackManager.getManager().push(this);
        initConfig(savedInstanceState);
        setContentView(setLayoutId());
        unbinder = ButterKnife.bind(this);
        context = this;
        activity = this;
        daoSession = getDaoSession();
        userId = getLoginUser();
        Bundle bundle = getIntent().getExtras();
        initBundleData(bundle);
        doBusiness();
    }

    /**
     * 获取daoSession
     */
    protected DaoSession getDaoSession() {
        return DaoUtils.getDaoSession();
    }

    /**
     * 获取配置
     */
    protected List<Configure> getConfigureList() {
        return DaoUtils.getConfigureList();
    }

    /**
     * 获取当前登录用户ID
     */
    protected String getLoginUser() {
        return DaoUtils.getLoginUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG, TAG + "-->onStart()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, TAG + "-->onResume()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onResume();
        }
        StatService.onResume(context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, TAG + "-->onPause()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onPause();
        }
        StatService.onPause(context);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, TAG + "-->onStop()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onStop();
        }
        StatService.onStop(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, TAG + "-->onDestroy()");
        ActivityStackManager.getManager().remove(this);
        if (lifeCycleListener != null) {
            lifeCycleListener.onDestroy();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        StatService.onStop(context);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(TAG, TAG + "-->onRestart()");
        if (lifeCycleListener != null) {
            lifeCycleListener.onRestart();
        }
    }

    /**
     * 初始化Activity配置,
     */
    protected void initConfig(Bundle savedInstanceState) {

    }

    /**
     * 初始化Bundle参数
     *
     * @param bundle
     */
    protected void initBundleData(Bundle bundle) {

    }

    /**
     * 获取 xml layout
     */
    protected abstract int setLayoutId();

    /**
     * 业务逻辑代码
     */
    protected abstract void doBusiness();

    /**
     * 设置生命周期监听
     *
     * @param lifecycleListener
     */
    public void setOnLifeCycleListener(ActivityLifeCycleListener lifecycleListener) {
        this.lifeCycleListener = lifecycleListener;
    }

    /**
     * 页面跳转
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 页面携带数据跳转
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 包含回调的页面跳转
     *
     * @param clazz
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带动画的页面跳转
     *
     * @param clazz
     * @param options ActivityOptionsCompat.makeSceneTransitionAnimation()
     */
    public void startActivityWithOptions(Class<?> clazz, Bundle options) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (options != null) {
            startActivity(intent, options);
        }
    }

    /**
     * 带数据和动画的页面跳转
     *
     * @param clazz
     * @param bundle  数据
     * @param options ActivityOptionsCompat.makeSceneTransitionAnimation()
     */
    public void startActivity(Class<?> clazz, Bundle bundle, Bundle options) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (options != null) {
            startActivity(intent, options);
        }
    }

    /**
     * 包含回调和数据的页面跳转
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 是否允许全屏
     *
     * @param isAllowFullScreen
     */
    public void setAllowFullScreen(boolean isAllowFullScreen) {
        if (isAllowFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void hideToolBar(boolean hide) {
        if (hide) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            //requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 设置屏幕旋转
     *
     * @param rotate SENSOR根据传感器自动旋转 PORTRAIT竖屏 LANDSCAPE横屏
     */
    public void setScreenRotate(int rotate) {
        switch (rotate) {
            case SENSOR:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
            case PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    /**
     * 是否设置沉浸状态栏
     *
     * @param isSetStatusBar
     */
    public void setImmersiveStatusBar(boolean isSetStatusBar) {
        if (isSetStatusBar) {
            StatusBarUtils.setImmersiveStatusBar(this.getWindow());
        }
    }

    /**
     * 使布局背景填充状态栏
     */
    public void setLayoutNoLimits(boolean isNoLimits) {
        // 布局背景填充状态栏 与键盘监听冲突
        if (isNoLimits) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
    }

    /**
     * 设置状态栏字体为深色
     * 注意：如果同时设置了沉浸状态栏，如果开启沉浸状态栏，必须在设置沉浸状态栏之后调用
     *
     * @param isDeepColor
     */
    public void setDeepColorStatusBar(boolean isDeepColor) {
        if (isDeepColor) {
            StatusBarUtils.setDeepColorStatusBar(this.getWindow());
        }
    }

    /**
     * 是否设置滑动退出
     * 注意：需要在主题中设置<item name="android:windowIsTranslucent">true</item>，否则将显示异常
     *
     * @param isSlideExit
     */
    public void setSlideExit(boolean isSlideExit) {
        if (isSlideExit) {
            SlidrUtils.setSlidrExit(this);
        }
    }
    /**
     * 打印调试级别日志
     *
     * @param format
     * @param args
     */
    protected void logDebug(String format, Object... args) {
        logMessage(Log.DEBUG, format, args);
    }

    /**
     * 打印信息级别日志
     *
     * @param format
     * @param args
     */
    protected void logInfo(String format, Object... args) {
        logMessage(Log.INFO, format, args);
    }

    /**
     * 打印错误级别日志
     *
     * @param format
     * @param args
     */
    protected void logError(String format, Object... args) {
        logMessage(Log.ERROR, format, args);
    }

    /**
     * 展示短时Toast
     *
     * @param format
     * @param args
     */
    protected void showShortToast(String format, Object... args) {
        showToast(Toast.LENGTH_SHORT, format, args);
    }

    /**
     * 展示长时Toast
     *
     * @param format
     * @param args
     */
    protected void showLongToast(String format, Object... args) {
        showToast(Toast.LENGTH_LONG, format, args);
    }

    /**
     * 打印日志
     *
     * @param level
     * @param format
     * @param args
     */
    private void logMessage(int level, String format, Object... args) {
        String formattedString = String.format(format, args);
        switch (level) {
            case Log.DEBUG:
                Log.d(TAG, formattedString);
                break;
            case Log.INFO:
                Log.i(TAG, formattedString);
                break;
            case Log.ERROR:
                Log.e(TAG, formattedString);
                break;
        }
    }

    /**
     * 展示Toast
     *
     * @param duration
     * @param format
     * @param args
     */
    private void showToast(int duration, String format, Object... args) {
        Toast.makeText(context, String.format(format, args), duration).show();
    }

}
