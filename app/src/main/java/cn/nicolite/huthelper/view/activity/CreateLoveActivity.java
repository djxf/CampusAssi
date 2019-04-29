package cn.nicolite.huthelper.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.presenter.CreateLovePresenter;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.ImageAdapter;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.iview.ICreateLoveView;

public class CreateLoveActivity extends BaseActivity<IBaseView,BaseActivity> implements ICreateLoveView {

    @BindView(R.id.toolbar_back)
    ImageView toolbar_back;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_menu)
    ImageView toolbar_menu;
    @BindView(R.id.et_add_love_content)
    TextInputEditText et_add_love_content;
    @BindView(R.id.love_add_pic)
    ImageView love_add_pic;
    @BindView(R.id.love_recyclerView)
    RecyclerView love_recyclerView;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    private List<Uri> uriList = new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE = 149;
    private ImageAdapter adapter;
    private ProgressDialog progressDialog;
    private CreateLovePresenter createLovePresenter;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_create_love;
    }

    @Override
    protected void doBusiness() {
            toolbar_title.setText("发布表白");
            love_recyclerView.setLayoutManager(new LinearLayoutManager(context,OrientationHelper.HORIZONTAL, false));
            adapter = new ImageAdapter(context, uriList, ImageAdapter.URI);
            love_recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, final int position, long itemId) {
                    final CommonDialog commonDialog = new CommonDialog(context);
                    commonDialog
                            .setMessage("确认删除这张图片？")
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonDialog.dismiss();
                                    uriList.remove(uriList.get(position));
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("不了", null)
                            .show();
                }

            });

            createLovePresenter = new CreateLovePresenter(this,this);

    }
    @OnClick({R.id.toolbar_back,R.id.toolbar_menu,R.id.love_add_pic})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.love_add_pic:
                createLovePresenter.selectImage();
                break;
            case R.id.toolbar_menu:
                createLovePresenter.createSay(this,uriList);
                LogUtils.d("list","uriList.size="+uriList.size());
                break;
        }
    }



    @Override
    public void selectImages() {
        try {
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG,MimeType.GIF))
                    .countable(true)
                    .maxSelectable(1 - uriList.size())
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.80f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);
        }catch (Throwable e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    @Override
    public void uploadLoveInfo(String hidden) {
            createLovePresenter.uploadLoveInfo(et_add_love_content.getText().toString(),hidden);
    }

    @Override
    public void uploadProgress(String msg) {
        ToastUtils.showToastLong("正在发布中");
    }

    @Override
    public void uploadFailure(String msg) {
        SnackbarUtils.showShortSnackbar(rootView, msg);
    }

    @Override
    public void publishSuccess() {

    }

    @Override
    public void showLoading() {
           if (progressDialog == null){
               progressDialog = new ProgressDialog(context);
               progressDialog.setMessage("请稍等...");
               progressDialog.setCancelable(false);
               progressDialog.show();
           }

    }

    @Override
    public void closeLoading() {
            if (progressDialog!=null){
                progressDialog.dismiss();
            }
    }

    @Override
    public void showMessage(String msg) {
        SnackbarUtils.showShortSnackbar(rootView, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            uriList.addAll(Matisse.obtainResult(data));
            adapter.notifyDataSetChanged();
        }
    }
}
