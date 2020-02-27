package cn.nicolite.huthelper.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.presenter.FeedBackPresenter;
import cn.nicolite.huthelper.utils.KeyBoardUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.view.iview.IFeedBackView;

/**
 * 反馈界面
 * Created by djxf on 2020年1月19日.
 */

public class FeedBackActivity extends BaseActivity<IBaseView, BaseActivity> implements IFeedBackView {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.et_feedbk_content)
    TextInputEditText etFeedbkContent;
    @BindView(R.id.et_feedbk_tel)
    TextInputEditText etFeedbkTel;
    @BindView(R.id.ll_feebk_success)
    LinearLayout llFeebkSuccess;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.ll_feedbk)
    LinearLayout llFeedbk;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_report)
    TextView tv_report;
    private FeedBackPresenter feedBackPresenter;
    private String title = "反馈";
    private String content;
    @Override
    protected void initConfig(Bundle savedInstanceState) {
        setImmersiveStatusBar(true);
        setDeepColorStatusBar(true);
        setSlideExit(true);
    }

    @Override
    protected void initBundleData(Bundle bundle) {
        if (bundle!=null){
            title = bundle.getString("title");
            tv_report.setText(title);
            content = bundle.getString("content");
            etFeedbkContent.setText(content);
            tv_msg.setVisibility(View.INVISIBLE);
            etFeedbkTel.setVisibility(View.INVISIBLE);
            etFeedbkContent.setSelection(content.length());
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void doBusiness() {
        toolbarTitle.setText(title);
        feedBackPresenter = new FeedBackPresenter(this, this);
    }

    @OnClick({R.id.toolbar_back, R.id.btn_feedbk_ok, R.id.btn_feedbk_finish, R.id.btn_feedbk_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.btn_feedbk_ok:
                KeyBoardUtils.hideSoftInput(context, getWindow());
                feedBackPresenter.feeBack(etFeedbkContent.getText().toString(), etFeedbkTel.getText().toString());
                break;
            case R.id.btn_feedbk_finish:
                finish();
                break;
            case R.id.btn_feedbk_again:
                llFeebkSuccess.setVisibility(View.GONE);
                llFeedbk.setVisibility(View.VISIBLE);
                etFeedbkContent.setText("");
                break;
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
    public void onSuccess() {
        llFeedbk.setVisibility(View.GONE);
        llFeebkSuccess.setVisibility(View.VISIBLE);
    }

}
