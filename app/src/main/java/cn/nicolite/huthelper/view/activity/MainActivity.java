package cn.nicolite.huthelper.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.stat.StatConfig;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.manager.ActivityStackManager;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.model.bean.Menu;
import cn.nicolite.huthelper.model.bean.Notice;
import cn.nicolite.huthelper.model.bean.SlidePic;
import cn.nicolite.huthelper.model.bean.TimeAxis;
import cn.nicolite.huthelper.model.bean.User;
import cn.nicolite.huthelper.network.api.SchoolAPI;
import cn.nicolite.huthelper.presenter.MainPresenter;
import cn.nicolite.huthelper.utils.ButtonUtils;
import cn.nicolite.huthelper.utils.DateUtils;
import cn.nicolite.huthelper.utils.DensityUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.GlideImageLoader;
import cn.nicolite.huthelper.view.adapter.MenuAdapter;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.customView.DateLineView;
import cn.nicolite.huthelper.view.customView.DateLineView2;
import cn.nicolite.huthelper.view.customView.DragLayout;
import cn.nicolite.huthelper.view.customView.RichTextView;
import cn.nicolite.huthelper.view.iview.IMainView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 主页
 */
public class MainActivity extends BaseActivity<IBaseView, BaseActivity> implements IMainView,OnBannerListener{

    @BindView(R.id.unReadMessage)
    FrameLayout unReadMessage;
    @BindView(R.id.tv_wd_temp)
    TextView tvWdTemp;
    @BindView(R.id.tv_wd_location)
    TextView tvWdLocation;
    @BindView(R.id.tv_course_maincontent)
    TextView tvCourseMaincontent;
    @BindView(R.id.tv_date_maincontent)
    TextView tvDateMaincontent;
    @BindView(R.id.iv_dateline)
    ImageView ivDateline;
    @BindView(R.id.dateLineView)
    DateLineView dateLineView;//白色
    @BindView(R.id.dateLineView2)
    DateLineView2 dateLineView2;//黑色
    @BindView(R.id.mu_tongzhi)
    ImageView muTongzhi;
    @BindView(R.id.tv_tongzhi_title)
    TextView tvTongzhiTitle;
    @BindView(R.id.tv_notice_maincontent)
    RichTextView tvNoticeMaincontent;
    @BindView(R.id.tv_tongzhi_contont)
    TextView tvTongzhiContont;
    @BindView(R.id.tv_tongzhi_time)
    TextView tvTongzhiTime;
    @BindView(R.id.rl_main_tongzhi)
    RelativeLayout rlMainTongzhi;
    @BindView(R.id.rv_main_menu)
    RecyclerView rvMainMenu;
    @BindView(R.id.rootView)
    DragLayout rootView;
    @BindView(R.id.iv_nav_avatar)
    ImageView ivNavAvatar;
    @BindView(R.id.tv_nav_name)
    RichTextView tvNavName;
    @BindView(R.id.tv_nav_private_message)
    RichTextView sixin;
    @BindView(R.id.imgbt_adsetting)
    ImageView adSeting;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.imgbtn_bell)
    ImageView im_msg_bt;
    private MainPresenter mainPresenter;
    private long exitTime = 0;
    private List<Menu> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private boolean isOpen;
    private QBadgeView qBadgeView;
    private Configure configure;
    private Notice notice;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
    private MainReceiver mainReceiver;
    static SlidePic slidePic;
    private String  SCHOOL_MAPURL;
    protected PopupWindow adListWindow;
    private int adPosition = 0;
    //设置图片资源:url或本地资源
    private ArrayList<String> images_url= new ArrayList<String>();
    //设置目标url
     private ArrayList<String>  target_url = new ArrayList<String>();
    //设置目标url
    ArrayList<String>  target_title = new ArrayList<String>();
    //设置时间轴的颜色
    ArrayList<String>  timeline_color = new ArrayList<String>();

    private final MyHandler mHandler = new MyHandler(this);

    private  class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity) {
            this.mActivity = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = mActivity.get();
            if (mainActivity == null) {
                return;
            }
            //暂时进行一次捕获防止后台数据崩溃
            try {
                slidePic =(SlidePic)msg.obj;
                SCHOOL_MAPURL = slidePic.getData().getMap_url();

                for (SlidePic.Informatin.BannerPic i : slidePic.getData().getBanner_pics()) {
                    images_url.add(i.getPic_url());
                    target_url.add(i.getTarget_url());
                    target_title.add(i.getTarget_title());
                    timeline_color.add(i.getTimeline_color());
                }

                //设置时间轴颜色
                if (timeline_color.size()!=0 && timeline_color != null){
                    dateLineView.setVisibility(View.GONE);//白色gone设为黑色
                    tvCourseMaincontent.setTextColor(Color.BLACK);
                }else {
                    dateLineView2.setVisibility(View.GONE);//黑色
                }
                if (banner == null){
                    tvCourseMaincontent.setTextColor(Color.WHITE);
                    dateLineView2.setVisibility(View.GONE);//黑色
                    dateLineView.setVisibility(View.VISIBLE);
                }
                SharedPreferences sp = getSharedPreferences(getConfigureList().get(0).getUserId()+"im_count",MODE_PRIVATE);
                int im_count = sp.getInt("im_count",0);

                if (slidePic.getData().getIm_msg_count() > im_count){
                    im_msg_bt.setImageDrawable(getResources().getDrawable(R.drawable.new_img));
                }else {
                    im_msg_bt.setImageDrawable(getResources().getDrawable(R.drawable.bell));
                }

                //存储消息计数器
                SharedPreferences.Editor editor = getSharedPreferences(getConfigureList().get(0).getUserId()+"im_count",MODE_PRIVATE).edit();
                editor.putInt("im_count",slidePic.getData().getIm_msg_count());
                editor.apply();

            }catch (Exception e){
                e.printStackTrace();
            }
            //展示广告
            showAds(images_url);
        }
    }

    /**
     * 展示广告方法
     *
     * @param list
     */
    public void showAds(List list){
        //防止空指针异常
        if (banner != null){
            //设置banner样式
            banner.setBannerStyle(BannerConfig.RIGHT);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(list);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.ZoomOut);
            // 设置自动轮播，默认为true
            banner.isAutoPlay(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
            banner.setAnimation(alphaAnimation);
            // 设置轮播时间
            if (images_url.size()==1){
                banner.setDelayTime(1000*60);
            }else {
                banner.setDelayTime(6000);
            }
            //设置监听器
            banner.setPageTransformer(true, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(@android.support.annotation.NonNull View page, float position) {
                    float alpha = 0.0f;
                    if (0.0f <= position && position <= 1.0f) {
                        alpha = 1.0f - position;
                    } else if (-1.0f <= position && position < 0.0f) {
                        alpha = position + 1.0f;
                    }
                    page.setAlpha(alpha);
                }
            });
            //Page监听
            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    adPosition = position;
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        if (banner != null){
            // banner设置方法全部调用完毕时最后调用
            if (images_url.size()!=0 || !images_url.isEmpty()){
                SharedPreferences ad_share = getSharedPreferences("adSp",MODE_PRIVATE);
                if (ad_share.getBoolean(DateUtils.getDate(),true)){
                    banner.start();
                    adSeting.setVisibility(View.VISIBLE);
                }else {
                    changeColor();
                }
            }
        }
    }
    protected void initConfig(Bundle savedInstanceState) {
        hideToolBar(true);
        setImmersiveStatusBar(true);
        setLayoutNoLimits(true);
    }

    @Override
    protected void initBundleData(Bundle bundle) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBusiness() {


        String userId = getLoginUser();

        List<Configure> configureList = getConfigureList();


        configure = configureList.get(0);
        if (configure.getUser() == null || TextUtils.isEmpty(userId) ||ListUtils.isEmpty(configureList) ){
            CommonDialog commonDialog = new CommonDialog(this);
            commonDialog.setMessage("当前登录失效 请重新登录")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               startActivity(LoginActivity.class);
                               finish();
                            }
                        })
                        .show();
            startActivity(LoginActivity.class);

        }

        rootView.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                isOpen = true;
            }

            @Override
            public void onClose() {
                isOpen = false;
            }

            @Override
            public void onDrag(float percent) {

            }
        });

        //主界面view监听
        menuAdapter = new MenuAdapter(context, menuList);
        rvMainMenu.setAdapter(menuAdapter);
        rvMainMenu.setLayoutManager(new GridLayoutManager(context, 4, OrientationHelper.VERTICAL, false));
        menuAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!ButtonUtils.isFastDoubleClick()) {
                    try {
                        Menu menu = menuList.get(position);
                        Bundle bundle = new Bundle();
                        if (menu.getType() == WebViewActivity.TYPE_LIBRARY) {
                            String url = Constants.LIBRARY;
                            if (!TextUtils.isEmpty(configure.getLibraryUrl())) {
                                url = configure.getLibraryUrl() + "/opac/m/index";
                            }
                            bundle.putString("url", url);
                        } else if (menu.getType() == WebViewActivity.TYPE_HOMEWORK) {
                            bundle.putString("url", Constants.HOMEWORK + configure.getStudentKH() + "/" + configure.getAppRememberCode());
                        }else if(menu.getType() == WebViewActivity.TYPE_CLUB){
                            bundle.putString("url", Constants.CLUB_ACTIVITY);
                        }else if (menu.getType() == WebViewActivity.TYPE_S_CALENDAR){
                            bundle.putString("url", Constants.S_CALENDAR);
                        }else if (menu.getType() == WebViewActivity.TYPE_MAP){
                            bundle.putString("url",SCHOOL_MAPURL);
                        }else if (menu.getType() == WebViewActivity.TYPE_JXBM){
                            bundle.putString("url",Constants.JXBM);
                        }
                        if (menu.getType() == WebViewActivity.TYPE_JXBM){
                            bundle.putString("title", "[广告]"+menu.getTitle());
                        }else {
                            bundle.putString("title", menu.getTitle());
                        }
                        bundle.putInt("type", menu.getType());

                        startActivityForResult(Class.forName(menu.getPath().trim()), bundle, Constants.REQUEST);
                    } catch (ClassNotFoundException e) {
                        showMessage("找不到该页面！");
                        e.printStackTrace();
                    }
                } else {
                    showMessage("你点的太快了！");
                }
            }
        });

        mainPresenter = new MainPresenter(this, this);
        mainPresenter.showMenu();
        mainPresenter.checkUpdate();
        mainPresenter.initUser();
        mainPresenter.showTimeAxis();
        mainPresenter.showSyllabus();
        mainPresenter.checkPermission();
        mainPresenter.registerPush();
        mainPresenter.showNotice(false);
        mainPresenter.startLoginService();
        bannerSet();



        //上传帐号信息到腾讯MTA
        StatConfig.setCustomUserId(context, configure.getStudentKH());
        //设置用户ID，已定位到用户级别的Crash记录
        CrashReport.setUserId(configure.getStudentKH());

        //注册本地广播监听消息
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        mainReceiver = new MainReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.MainBroadcast);
        localBroadcastManager.registerReceiver(mainReceiver, intentFilter);

    }

    //主界面监听器
    class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int type = bundle.getInt("type");
                switch (type) {
                    case Constants.BROADCAST_TYPE_NOTICE:
                        mainPresenter.showNotice(true);
                        break;
                    case Constants.BROADCAST_TYPE_REFRESH_AVATAR:
                        showUser(configure.getUser());
                        break;
                }
            }
        }
    }


    @OnClick({R.id.iv_nav_avatar, R.id.tv_nav_private_message,R.id.tv_nav_name,
            R.id.tv_nav_update, R.id.tv_nav_share, R.id.tv_nav_logout, R.id.tv_nav_about,
            R.id.tv_nav_fback, R.id.imgbtn_menusetting, R.id.imgbtn_bell,
            R.id.tv_tongzhi_contont, R.id.tv_tongzhi_title, R.id.tv_notice_maincontent,R.id.imgbt_adsetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nav_name:
            case R.id.iv_nav_avatar:
                Bundle bundle = new Bundle();
                bundle.putInt("type", ContainerActivity.TYPE_USER_LIST);
                bundle.putString("title", "个人信息");
                startActivity(ContainerActivity.class, bundle);
                break;
            case R.id.tv_nav_private_message:
                mainPresenter.startChat();
                break;
            case R.id.tv_nav_update:
                Beta.checkUpgrade();
                break;
            case R.id.tv_nav_share:
                mainPresenter.share();
                break;
            case R.id.tv_nav_logout:
                final CommonDialog commonDialog = new CommonDialog(context);
                commonDialog
                        .setMessage("确定退出？")
                        .setPositiveButton("是的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XGPushManager.deleteTag(getApplicationContext(), configure.getStudentKH());
                                XGPushManager.registerPush(getApplicationContext(), "*");
                                XGPushManager.unregisterPush(getApplicationContext());
                                startActivity(LoginActivity.class);
                                commonDialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("再想想", null)
                        .show();
                break;
            case R.id.tv_nav_about:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", ContainerActivity.TYPE_ABOUT);
                bundle1.putString("title", "关于");
                startActivity(ContainerActivity.class, bundle1);
                break;
            case R.id.tv_nav_fback:
                startActivity(FeedBackActivity.class);
                break;
            case R.id.imgbtn_menusetting:
                rootView.open();
                break;
            case R.id.imgbtn_bell:
                String url = String.format(Constants.IMLIST,getConfigureList().get(0).getUserId(),getConfigureList().get(0).getAppRememberCode());
                Bundle cBundle = new Bundle();
                cBundle.putString("url",url);
                cBundle.putInt("type",888);
                cBundle.putString("title","私信");
                im_msg_bt.setImageDrawable(getResources().getDrawable(R.drawable.bell));
                startActivity(WebViewActivity.class,cBundle);
                break;
            case R.id.tv_tongzhi_title:
            case R.id.tv_tongzhi_contont:
                if (notice != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putLong("noticeId", notice.getId());
                    startActivity(NoticeItemActivity.class, bundle2);
                }
                break;
            case R.id.tv_notice_maincontent:
                startActivity(NoticeActivity.class);
                break;
            case R.id.imgbt_adsetting:
                //广告处理
                handleAds();
                break;
        }
    }
    private void changeColor(){
        tvCourseMaincontent.setTextColor(Color.WHITE);
        dateLineView2.setVisibility(View.GONE);//黑色
        dateLineView.setVisibility(View.VISIBLE);
    }


    private void handleAds() {
        //广告处理
        View adPupMenu = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_adset_choose,null);
        TextView tvClose = (TextView) adPupMenu.findViewById(R.id.tv_popmenu_close);
        TextView tvAdd = (TextView) adPupMenu.findViewById(R.id.tv_popmenu_adContact);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adListWindow.dismiss();
                if (banner!=null) {
                    //移除当前数据
                    if (images_url.size()>1 && target_url.size()>1 && target_title.size()>1){
                        images_url.remove(adPosition);
                        target_title.remove(adPosition);
                        target_url.remove(adPosition);
                        showAds(images_url);
                    }else {
                        banner.setVisibility(View.INVISIBLE);
                        adSeting.setVisibility(View.INVISIBLE);
                        SharedPreferences.Editor ad_editor =  getSharedPreferences("adSp",MODE_PRIVATE).edit();
                        ad_editor.putBoolean(DateUtils.getDate(),false);
                        ad_editor.putInt("imageUrlSize",images_url.size());
                        ad_editor.apply();
                        changeColor();
                    }
                }
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adListWindow = new PopupWindow(adPupMenu, DensityUtils.dp2px(MainActivity.this, 170),
                DensityUtils.dp2px(MainActivity.this, 90));
        adListWindow.setFocusable(true);
        adListWindow.setOutsideTouchable(true);
        adListWindow.setBackgroundDrawable(new BitmapDrawable());
        adListWindow.showAsDropDown(adSeting, -DensityUtils.dp2px(MainActivity.this, 115), 5);
    }

    @Override
    public void onBackPressed() {
        if (isOpen) {
            rootView.close(true);
        } else if (System.currentTimeMillis() - exitTime > 2000) {
            SnackbarUtils.showShortSnackbar(rootView, "再按一次返回键退出");
            exitTime = System.currentTimeMillis();
        } else {
            // super.onBackPressed();
            ActivityStackManager.getManager().exitApp(context);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showMessage(String msg) {
        SnackbarUtils.showShortSnackbar(rootView, msg);
    }

    @Override
    public void showWeather(String city, String tmp, String content) {
        tvWdLocation.setText(String.valueOf(city + "|" + content));
        tvWdTemp.setText(String.valueOf(tmp + "℃"));
    }

    @Override
    public void showTimeAxis(List<TimeAxis> timeAxisList) {
        dateLineView.setDateLineData(timeAxisList);
        dateLineView2.setDateLineData(timeAxisList);
    }

    @Override
    public void showNotice(final Notice notice, boolean isReceiver) {
        this.notice = notice;
        tvTongzhiTitle.setText(notice.getTitle());
        tvTongzhiContont.setText(notice.getContent());

        if (isReceiver) {
            final CommonDialog commonDialog = new CommonDialog(context);
            commonDialog
                    .setTitle(notice.getTitle())
                    .setMessage(notice.getContent())
                    .setPositiveButton("查看", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            commonDialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putLong("noticeId", notice.getId());
                            startActivity(NoticeItemActivity.class, bundle);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    @Override
    public void showSyllabus(String date, String nextClass) {
        tvDateMaincontent.setText(date);
        tvCourseMaincontent.setText(nextClass);
    }

    @Override
    public void showMenu(List<Menu> menuList) {
        this.menuList.clear();
        this.menuList.addAll(menuList);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUser(User user) {
        tvNavName.setText(user.getTrueName());
        String headPic = Constants.PICTURE_URL + (TextUtils.isEmpty(user.getHead_pic()) ? user.getHead_pic_thumb() : user.getHead_pic());

        if (!TextUtils.isEmpty(headPic)) {
            Glide
                    .with(activity)
                    .load(headPic)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .crossFade()
                    .into(ivNavAvatar);
        } else {
            int headPicD = user.getSex().equals("男") ? R.drawable.head_boy : R.drawable.head_girl;
            Glide
                    .with(activity)
                    .load(headPicD)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .crossFade()
                    .into(ivNavAvatar);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST) {
            switch (resultCode) {
                case Constants.CHANGE:
                    mainPresenter.showMenu();
                    break;
                case Constants.REFRESH:
                    mainPresenter.showSyllabus();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(mainReceiver);
        }

    }

    public void bannerSet() {
        if (banner != null){
            banner.setOnBannerListener(this);
        }

        //请求回资源
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SchoolAPI schoolAPI = retrofit.create(SchoolAPI.class);
        Call<SlidePic> call = schoolAPI.slideShow(configure.getStudentKH(),configure.getAppRememberCode());
        call.enqueue(new Callback<SlidePic>() {
            @Override
            public void onResponse(Call<SlidePic> call, final Response<SlidePic> response) {
                Message message  = new Message();
                message.obj = response.body();
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SlidePic> call, Throwable t) {
                t.printStackTrace();
                mHandler.sendEmptyMessage(0x401);
            }
        });
    }
    @Override
    public void OnBannerClick(int position) {
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", WebViewActivity.TYPE_OPEN_SOURCE);
        bundle3.putString("url", target_url.get(position));
        bundle3.putString("title",target_title.get(position));
        this.startActivity(WebViewActivity.class, bundle3);
    }
}
