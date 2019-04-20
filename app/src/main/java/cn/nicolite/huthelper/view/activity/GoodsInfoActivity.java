package cn.nicolite.huthelper.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.GoodsItem;
import cn.nicolite.huthelper.presenter.GoodsInfoPresenter;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.adapter.ImageAdapter;
import cn.nicolite.huthelper.view.iview.IGoodsInfoView;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * 商品详情页
 * Created by nicolite on 17-11-9.
 */

public class GoodsInfoActivity extends BaseActivity<IBaseView, BaseActivity> implements IGoodsInfoView {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_user)
    ImageView toolbarUser;
    @BindView(R.id.toolbar_delete)
    ImageView toolbarDelete;
    @BindView(R.id.tv_text_title)
    TextView tvTextLostTitle;
    @BindView(R.id.tv_text_content)
    TextView tvTextLost;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_quality)
    TextView tvGoodsQuality;
    @BindView(R.id.tv_goods_tel)
    TextView tvGoodsTel;
    @BindView(R.id.tv_goods_location)
    TextView tvGoodsLocation;
    @BindView(R.id.iv_num_lost)
    TextView ivNumLost;
    @BindView(R.id.tv_sendtime_lost)
    TextView tvSendtimeLost;
    @BindView(R.id.rootView)
    CoordinatorLayout rootView;
    @BindView(R.id.iv_bgimage)
    ImageView ivBgimage;
    @BindView(R.id.rv_goods_images)
    RecyclerView recyclerView;
    private String mUserId;
    private boolean delete;
    private GoodsInfoPresenter goodsInfoPresenter;
    private String goodsId;
    private String phone;
    private List<String> imageList = new ArrayList<>();
    private List<String> imageSrcList = new ArrayList<>();
    private ImageAdapter adapter;
    private String username;
    private int position;

    @Override
    protected void initConfig(Bundle savedInstanceState) {
        setImmersiveStatusBar(true);
        setLayoutNoLimits(true);
        setSlideExit(true);
    }

    @Override
    protected void initBundleData(Bundle bundle) {
        if (bundle != null) {
            goodsId = bundle.getString("goodsId", "");
            mUserId = bundle.getString("userId", "");
            delete = bundle.getBoolean("delete", false);
            position = bundle.getInt("position", -1);
            if (TextUtils.isEmpty(mUserId) || TextUtils.isEmpty(goodsId) || position == -1) {
                ToastUtils.showToastShort("获取信息失败！");
                finish();
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void doBusiness() {
        toolbarTitle.setText("商品详情");
        if (delete || mUserId.equals(userId)) {
            toolbarUser.setVisibility(View.GONE);
            toolbarDelete.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false));
        adapter = new ImageAdapter(context, imageList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long itemId) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", (ArrayList<String>) imageSrcList);
                bundle.putInt("curr", position);
                startActivity(ShowImageActivity.class, bundle);
            }
        });
        goodsInfoPresenter = new GoodsInfoPresenter(this, this);
        goodsInfoPresenter.showGoodsInfo(goodsId);
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_user, R.id.toolbar_delete, R.id.tv_goods_tel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_user:
                Bundle bundle = new Bundle();
                bundle.putString("userId", mUserId);
                bundle.putString("username", username);
                startActivity(UserInfoCardActivity.class, bundle);
                break;
            case R.id.toolbar_delete:
                final CommonDialog commonDialog = new CommonDialog(context);
                commonDialog
                        .setMessage("确认删除？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goodsInfoPresenter.deleteGoods(goodsId);
                                commonDialog.dismiss();
                            }
                        })
                        .setNegativeButton("不了", null)
                        .show();
                break;
            case R.id.tv_goods_tel:
                if (!TextUtils.isEmpty(phone) && TextUtils.isDigitsOnly(tvGoodsTel.getText().toString())) {
                    Intent dial = new Intent(Intent.ACTION_DIAL);
                    dial.setData(Uri.parse("tel:" + phone));
                    dial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dial);
                }
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
    public void showGoodsInfo(GoodsItem goodsItem) {
        phone = goodsItem.getPhone();
        username = goodsItem.getUsername();
        imageList.clear();
        imageList.addAll(goodsItem.getPics());
        imageSrcList.clear();
        imageSrcList.addAll(goodsItem.getPics_src());
        tvSendtimeLost.setText(goodsItem.getCreated_on());
        tvTextLostTitle.setText(goodsItem.getTit());
        tvTextLost.setText(goodsItem.getContent());
        tvGoodsQuality.setText(goodsItem.getAttr());
        ivNumLost.setText(goodsItem.getUsername());
        tvGoodsPrice.setText(String.valueOf("￥" + goodsItem.getPrize()));
        tvGoodsTel.setText(TextUtils.isEmpty(phone) ? "无联系方式" : phone);
        tvGoodsLocation.setText(TextUtils.isEmpty(goodsItem.getAddress()) ? "湖工大" : goodsItem.getAddress());

        if (!ListUtils.isEmpty(imageList)) {
            Glide
                    .with(this)
                    .load(Constants.PICTURE_URL + imageList.get(0))
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.blur_plac_min)
                    .error(R.drawable.blur_plac_min)
                    .bitmapTransform(new BlurTransformation(context, 100), new ColorFilterTransformation(this, 0x29000000))
                    .crossFade()
                    .into(ivBgimage);

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess() {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(Constants.DELETE, intent);
        finish();
    }

}
