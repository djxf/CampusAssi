package cn.nicolite.huthelper.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.nicolite.huthelper.BuildConfig;
import cn.nicolite.huthelper.app.MApplication;
import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.db.dao.LessonDao;
import cn.nicolite.huthelper.db.dao.MenuDao;
import cn.nicolite.huthelper.db.dao.NoticeDao;
import cn.nicolite.huthelper.db.dao.TimeAxisDao;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.Lesson;
import cn.nicolite.huthelper.model.bean.Menu;
import cn.nicolite.huthelper.model.bean.Notice;
import cn.nicolite.huthelper.model.bean.SlidePic;
import cn.nicolite.huthelper.model.bean.TimeAxis;
import cn.nicolite.huthelper.model.bean.Update;
import cn.nicolite.huthelper.model.bean.User;
import cn.nicolite.huthelper.model.bean.Weather;
import cn.nicolite.huthelper.network.APIUtils;
import cn.nicolite.huthelper.network.api.SchoolAPI;
import cn.nicolite.huthelper.network.exception.ExceptionEngine;
import cn.nicolite.huthelper.services.LoginService;
import cn.nicolite.huthelper.utils.CommUtil;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.view.activity.ContainerActivity;
import cn.nicolite.huthelper.view.activity.MainActivity;
import cn.nicolite.huthelper.view.activity.WebViewActivity;
import cn.nicolite.huthelper.view.adapter.GlideImageLoader;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.iview.IMainView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MainPresenter
 * Created by nicolite on 17-10-22.
 */

public class MainPresenter extends BasePresenter<IMainView, MainActivity> {


    public MainPresenter(IMainView view, MainActivity activity) {
        super(view, activity);
    }

    public void showWeather() {

        if (getView() != null) {
            getView().showWeather(configure.getCity(), configure.getTmp(), configure.getContent());
        }

        APIUtils.INSTANCE
                .getWeatherAPI()
                .getWeather()
                .compose(getActivity().<Weather>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (getView() != null) {
                            getView().showLoading();
                        }
                    }

                    @Override
                    public void onNext(@NonNull Weather weather) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showWeather(weather.getData().getCity(), weather.getData().getWendu(),
                                    weather.getData().getForecast().get(0).getType());

                            configure.setCity(weather.getData().getCity());
                            configure.setTmp(weather.getData().getWendu());
                            configure.setContent(weather.getData().getForecast().get(0).getType());
                            configure.update();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showTimeAxis() {
        final TimeAxisDao timeAxisDao = daoSession.getTimeAxisDao();
        final List<TimeAxis> list = timeAxisDao.queryBuilder().list();

        if (!ListUtils.isEmpty(list) && getView() != null) {
            getView().showTimeAxis(list);
        }

        APIUtils.INSTANCE
                .getDateLineAPI()
                .getTimeAxis()
                .compose(getActivity().<List<TimeAxis>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TimeAxis>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (getView() != null) {
                            getView().showLoading();
                        }
                    }

                    @Override
                    public void onNext(@NonNull List<TimeAxis> timeAxisList) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (!ListUtils.isEmpty(timeAxisList)) {
                                getView().showTimeAxis(timeAxisList);
                                timeAxisDao.deleteAll();
                                for (TimeAxis timeAxis : timeAxisList) {
                                    timeAxisDao.insert(timeAxis);
                                }

                            } else {
                                if (!ListUtils.isEmpty(list)) {
                                    getView().showTimeAxis(list);
                                }
                                getView().showMessage("未获取到时间轴数据！");
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showNotice(boolean isReceiver) {

        NoticeDao noticeDao = daoSession.getNoticeDao();
        List<Notice> list = noticeDao.queryBuilder()
                .where(NoticeDao.Properties.UserId.eq(userId))
                .orderDesc(NoticeDao.Properties.Id)
                .list();

        if (!ListUtils.isEmpty(list) && getView() != null) {
            getView().showNotice(list.get(0), isReceiver);
        }

    }

    public void showSyllabus() {
        LessonDao lessonDao = daoSession.getLessonDao();
        List<Lesson> list = lessonDao.queryBuilder()
                .where(LessonDao.Properties.UserId.eq(userId))
                .list();
        getView().showSyllabus(CommUtil.getData(), CommUtil.getNextClass(list));
    }

    public void showMenu() {

        MenuDao menuDao = daoSession.getMenuDao();
        List<Menu> menus = menuDao.queryBuilder()
                .where(MenuDao.Properties.UserId.eq(userId))
                .list();

        int newVersionCode = 45; //TODO 需要更新menu时，将这个数值加1

        SharedPreferences updateMainMenu = MApplication.appContext.getSharedPreferences("update_main_menu", Context.MODE_PRIVATE);
        int oldVersionCode = updateMainMenu.getInt("versionCode", -1);

        if (newVersionCode > oldVersionCode || ListUtils.isEmpty(menus)) {
            List<Menu> menuItems = new ArrayList<>();
            Menu item = new Menu(0, 0, WebViewActivity.TYPE_LIBRARY, "图书馆", "cn.nicolite.huthelper.view.activity.WebViewActivity", false);
            menuItems.add(item);
            item = new Menu(1, 1, 0, "课程表", "cn.nicolite.huthelper.view.activity.SyllabusActivity", true);
            menuItems.add(item);
            item = new Menu(2, 2, WebViewActivity.TYPE_EXAM, "考试计划", "cn.nicolite.huthelper.view.activity.WebViewActivity", true);
            menuItems.add(item);
            item = new Menu(3, 3, WebViewActivity.TYPE_GRADE, "成绩查询", "cn.nicolite.huthelper.view.activity.WebViewActivity", false);
            menuItems.add(item);
            item = new Menu(4, 18, 0, "全校课程", "cn.nicolite.huthelper.view.activity.SpareActivity", false);
            menuItems.add(item);
            item = new Menu(5, 5, 0, "二手市场", "cn.nicolite.huthelper.view.activity.MarketActivity", true);
            menuItems.add(item);
            item = new Menu(6, 6, 0, "校园说说", "cn.nicolite.huthelper.view.activity.SayActivity", true);
            menuItems.add(item);
            item = new Menu(7, 7, 0, "电费查询", "cn.nicolite.huthelper.view.activity.ElectricActivity", true);
            menuItems.add(item);
            item = new Menu(9, 20, WebViewActivity.TYPE_JXBM, "驾校报名", "cn.nicolite.huthelper.view.activity.WebViewActivity", true);
            menuItems.add(item);
            item = new Menu(10, 10, 0, "失物招领", "cn.nicolite.huthelper.view.activity.LostAndFoundActivity", true);
            menuItems.add(item);
            item = new Menu(11, 9, WebViewActivity.TYPE_S_CALENDAR, "校历", "cn.nicolite.huthelper.view.activity.WebViewActivity", true);
            menuItems.add(item);
            item = new Menu(12, 16, WebViewActivity.TYPE_MAP, "平面图", "cn.nicolite.huthelper.view.activity.WebViewActivity", true);
            menuItems.add(item);
            item = new Menu(15, 4, WebViewActivity.TYPE_HOMEWORK, "网上作业", "cn.nicolite.huthelper.view.activity.WebViewActivity", false);
            menuItems.add(item);
            item = new Menu(16, 11, 0, "宣讲会", "cn.nicolite.huthelper.view.activity.CareerTalkActivity", false);
            menuItems.add(item);
            item = new Menu(17, 19, WebViewActivity.TYPE_EMPTY_CLASSROOM, "空教室", "cn.nicolite.huthelper.view.activity.WebViewActivity", false);
            menuItems.add(item);
            item = new Menu(18, 8, 0, "实验课表", "cn.nicolite.huthelper.view.activity.ExpLessonActivity", true);
            menuItems.add(item);

            //全部放于最后一个
            item = new Menu(19, 12, 0, "全部", "cn.nicolite.huthelper.view.activity.AllActivity", true);
            menuItems.add(item);

            menuDao.deleteAll();
            for (Menu menu : menuItems) {
                menu.setUserId(userId);
                menuDao.insert(menu);
            }

            updateMainMenu.edit()
                    .putInt("versionCode", newVersionCode)
                    .apply();
        }

        List<Menu> menuList = menuDao.queryBuilder()
                .where(MenuDao.Properties.UserId.eq(userId), MenuDao.Properties.IsMain.eq(true))
                .orderAsc(MenuDao.Properties.Index)
                .limit(25)
                .list();

        if (getView() != null) {
            getView().showMenu(menuList);
        }

    }

    //必须调用此接口以记录该用户是否使用工大助手
    public void checkUpdate() {

        APIUtils.INSTANCE
                .getUpdateAPI()
                .checkUpdate(configure.getStudentKH(), configure.getAppRememberCode())
                .compose(getActivity().<HttpResult<Update>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult<Update>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpResult<Update> updateHttpResult) {
                        if (updateHttpResult.getCode() == 200) {
                            Update data = updateHttpResult.getData();
                            if (data == null) {
                                return;
                            }

                            configure.setLibraryUrl(data.getApi_base_address().getLibrary());
                            configure.setTestPlanUrl(data.getApi_base_address().getTest_plan());
                            configure.setNewTermDate(data.getSchool_opens());
                            configure.update();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void registerPush() {
        Context applicationContext = MApplication.appContext;
        XGPushConfig.enableDebug(applicationContext, BuildConfig.LOG_DEBUG);
        XGPushConfig.setMiPushAppId(applicationContext, Constants.XIAOMI_APP_ID);
        XGPushConfig.setMiPushAppKey(applicationContext, Constants.XIAOMI_APP_KEY);
        XGPushConfig.enableOtherPush(applicationContext, true);
        XGPushConfig.setHuaweiDebug(BuildConfig.LOG_DEBUG);
        XGPushManager.registerPush(getActivity().getApplicationContext(), configure.getStudentKH(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                LogUtils.d(TAG, "注册成功，设备token为：" + o);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                LogUtils.d(TAG, "注册失败，错误码：" + i + "，错误信息：" + s);
                if (getView() == null) {
                    return;
                }
                getView().showMessage("注册推送服务失败：" + s + " code：" + i);
            }
        });
        XGPushConfig.enableDebug(getActivity().getApplicationContext(), BuildConfig.LOG_DEBUG);
    }

    public void initUser() {

        User user = configure.getUser();
        if (user != null) {
            getView().showUser(user);
        }
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "工大助手下载连接：https://www.coolapk.com/apk/cn.nicolite.huthelper");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(Intent.createChooser(intent, "分享"));
    }

    public void startChat() {
        //TODO 开始聊天， 待添加
        final CommonDialog commonDialog = new CommonDialog(getActivity());
        commonDialog
                .setMessage("私信暂时下线，新的私信已经在路上了！")
                .setPositiveButton("知道了", null)
                .show();
    }

    public void checkPermission() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
        };
        AndPermission
                .with(getActivity())
                .requestCode(200)
                .permission(permissions)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @android.support.annotation.NonNull List<String> grantPermissions) {

                    }

                    @Override
                    public void onFailed(int requestCode, @android.support.annotation.NonNull List<String> deniedPermissions) {
                        if (getView() != null) {
                            getView().showMessage("获取权限失败，请授予文件读写和读取手机状态权限！");
                        }
                    }
                })
                .start();
    }

    public void startLoginService() {
        LoginService.start(MApplication.application);
    }



}



