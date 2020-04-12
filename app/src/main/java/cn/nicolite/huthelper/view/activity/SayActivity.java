package cn.nicolite.huthelper.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.model.bean.User;
import cn.nicolite.huthelper.presenter.SearchPresenter;
import cn.nicolite.huthelper.utils.DensityUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.ScreenUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.TabAdapter;
import cn.nicolite.huthelper.view.fragment.MarketFragment;
import cn.nicolite.huthelper.view.fragment.SayFragment;

/**
 * Created by nicolite on 17-11-13.
 */

public class SayActivity extends BaseActivity<IBaseView, BaseActivity> {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rootView)
    FrameLayout rootView;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.toolbar_menu)
    ImageView toolBarMenu;
    private SayFragment fragment;
    FragmentTransaction transaction;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    List<String> titleList = new ArrayList<>();
    private SayFragment followFragment;
    private SayFragment allFragment;
    private SayFragment hotFragment;


    @Override
    protected void initConfig(Bundle savedInstanceState) {

        setImmersiveStatusBar(true);
        setDeepColorStatusBar(true);
        setSlideExit(true);
    }

    @Override
    protected void initBundleData(Bundle bundle) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_say;
    }

    @Override
    protected void doBusiness() {

        toolbarTitle.setText("段子");
        viewpager.setAdapter(new TabAdapter(getSupportFragmentManager(), getTitleList(), getFragmentList()));
        tab.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(2);

    }

    private List<String> getTitleList() {
        titleList.add("关注");
        titleList.add("最热");
        titleList.add("最新");
        return titleList;
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_menu,R.id.toolbar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_menu:
                showMenuWindow(toolBarMenu);
                break;
            case R.id.toolbar_search:
                //搜索按钮 传值600
                Bundle bundle =new Bundle();
                bundle.putInt("type",600);
                startActivity(SearchActivity.class,bundle);
                break;
        }
    }

    protected PopupWindow menuListWindow;


    private void showMenuWindow(View parent) {
        if (menuListWindow == null) {
            if (TextUtils.isEmpty(userId)) {
                ToastUtils.showToastShort("获取用户信息失败！");
                return;
            }

            List<Configure> configureList = getConfigureList();

            if (ListUtils.isEmpty(configureList)) {
                ToastUtils.showToastShort("获取用户信息失败！");
                return;
            }
            final User user = configureList.get(0).getUser();

            if (user==null){
                ToastUtils.showToastLong("获取当前用户失败 请重新登录！");
                return;
            }
            View popupWindowLayout = LayoutInflater.from(context).inflate(R.layout.popup_say_choose,rootView,false);

            TextView tvMime = (TextView) popupWindowLayout.findViewById(R.id.tv_popmenu_mime);
            TextView tvAdd = (TextView) popupWindowLayout.findViewById(R.id.tv_popmenu_add);
            TextView tvHot = (TextView) popupWindowLayout.findViewById(R.id.tv_popmenu_hot);
            TextView tvTalk = (TextView) popupWindowLayout.findViewById(R.id.tv_popmenu_mytalk);
            tvAdd.setText("发布段子");
            tvMime.setText("我的发布");
            tvHot.setText("热门段子");
            tvTalk.setText("我的互动");

            tvTalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuListWindow.dismiss();
                    toolbarTitle.setText("我的互动");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_content,SayFragment.newInstance(SayFragment.MYTALK,null));
                    transaction.commit();
                }
            });
            tvHot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuListWindow.dismiss();
                    toolbarTitle.setText("热门段子");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_content,SayFragment.newInstance(SayFragment.HOTSAY,null));
                    transaction.commit();
                }
            });
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuListWindow.dismiss();
                    startActivityForResult(CreateSayActivity.class, Constants.REQUEST);
                }
            });

            tvMime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuListWindow.dismiss();
                    Bundle bundle = new Bundle();
                    if (user.getStudentKH() != null){
                        bundle.putInt("type", SearchPresenter.TYPE_MYSAY);
                        bundle.putString("searchText", user.getUser_id());
                        bundle.putString("extras",user.getUsername());
                        startActivity(SearchResultActivity.class, bundle);
                    }else {
                        ToastUtils.showToastLong("当前登录用户为空 请重新登录！");
                    }

                }
            });
            menuListWindow = new PopupWindow(popupWindowLayout, DensityUtils.dp2px(SayActivity.this, 170),
                    DensityUtils.dp2px(SayActivity.this, 200));
        }

        rootView.setForeground(getResources().getDrawable(R.color.bg_black_shadow));


        menuListWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rootView.setForeground(getResources().getDrawable(R.color.transparent));
            }
        });

        menuListWindow.setFocusable(true);
        menuListWindow.setOutsideTouchable(true);
        menuListWindow.setBackgroundDrawable(new BitmapDrawable());
        menuListWindow.showAsDropDown(parent, -DensityUtils.dp2px(SayActivity.this, 115), 20);
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();

        if (followFragment == null) {
            followFragment = SayFragment.newInstance(SayFragment.FOLLOW, null);
        }
        if (hotFragment == null) {
            hotFragment = SayFragment.newInstance(SayFragment.HOTSAY, null);
        }
        if (allFragment == null) {
            allFragment = SayFragment.newInstance(SayFragment.ALLSAY, null);
        }

        fragmentList.add(followFragment);
        fragmentList.add(hotFragment);
        fragmentList.add(allFragment);
        return fragmentList;
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从activity传递响应数据到fragment
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
