package cn.nicolite.huthelper.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.view.View
import cn.nicolite.huthelper.R
import cn.nicolite.huthelper.kBase.BaseFragment
import cn.nicolite.huthelper.model.Constants
import cn.nicolite.huthelper.model.bean.Code
import cn.nicolite.huthelper.model.bean.User
import cn.nicolite.huthelper.presenter.UserInfoPresenter
import cn.nicolite.huthelper.utils.*
import cn.nicolite.huthelper.view.activity.WebViewActivity
import cn.nicolite.huthelper.view.customView.CommonDialog
import cn.nicolite.huthelper.view.iview.IUserInfoView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_user_info_card.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import java.io.File


/**
 * Created by nicolite on 2018/5/30.
 * email nicolite@nicolite.cn
 */
class UserInfoFragment : BaseFragment(), IUserInfoView {



    private val userInfoPresenter: UserInfoPresenter = UserInfoPresenter(this, this)

    override fun setLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun doBusiness() {
        super.doBusiness()

        userInfoPresenter.showUserData()

        rl_user_headview.setOnClickListener {
            userInfoPresenter.changAvatar()
        }

        rl_user_nickname.setOnClickListener {
            val commonDialog = CommonDialog(context)
            commonDialog
                    .setTitle("请输入新的昵称")
                    .setInput()
                    .setPositiveButton("确认") {
                        val inputText = commonDialog.inputText
                        userInfoPresenter.changeUserName(inputText)
                        tv_user_nickname.text = inputText
                        commonDialog.dismiss()
                    }
                    .setNegativeButton("不改了", null)
                    .show()
        }

        rl_user_password.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", WebViewActivity.TYPE_CHANGE_PWD)
            bundle.putString("title", "修改密码")
            bundle.putString("url", Constants.CHANGE_PWD)
            startActivity(WebViewActivity::class.java, bundle)
        }

        rl_user_bio.setOnClickListener {
            val commonDialog2 = CommonDialog(context)
            commonDialog2
                    .setTitle("请输入新的签名")
                    .setInput()
                    .setPositiveButton("确认") {
                        val inputText = commonDialog2.inputText
                        userInfoPresenter.changeBio(inputText)
                        tv_user_bio_kt.text = inputText
                        commonDialog2.dismiss()
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }

        rl_user_college.setOnClickListener{
            val commonDialog3 = CommonDialog(context)
            commonDialog3
                    .setTitle("请输入新的学院加班级格式：学院-班级")
                    .setInput()
                    .setPositiveButton("确定"){
                        val  inputText = commonDialog3.inputText.replace('-','-')
                        var strlist : List<String> = inputText.split("-")
                        if (strlist.size==2){
                            userInfoPresenter.changeCollegeAndClass(strlist.get(0),strlist.get(1))
                            commonDialog3.dismiss()
                        }else{
                            ToastUtils.showToastLong("格式错误，-必须为英文状态下")
                        }

                    }
                    .setNegativeButton("不改了", null)
                    .show()
        }

        rl_user_class.setOnClickListener{
            val commonDialog4 = CommonDialog(context)
            commonDialog4
                    .setTitle("请输入新的学院加班级格式：学院-班级")
                    .setInput()
                    .setPositiveButton("确定"){
                        val  inputText = commonDialog4.inputText
                        var strlist : List<String> = inputText.split("-")
                        if (strlist.size==2){
                            userInfoPresenter.changeCollegeAndClass(strlist.get(0),strlist.get(1))
                            commonDialog4.dismiss()
                        }else{
                            ToastUtils.showToastLong("格式错误，-必须为英文状态下")
                        }
                    }
                    .setNegativeButton("不改了", null)
                    .show()
        }

        //修改qq
        rl_user_qq.setOnClickListener{
            val commonDialog = CommonDialog(context)
            commonDialog
                    .setTitle("请输入你的QQ（这意味着别人可以通过QQ联系你）")
                    .setInput()
                    .setPositiveButton("确定"){
                        val inputText = commonDialog.inputText
                        if (inputText.length<=6 || !CommUtil.isNumberic(inputText) || inputText.length >=12){
                            ToastUtils.showToastLong("请输入正确的QQ")
                        }else{
                            userInfoPresenter.changeQq(inputText)
                            commonDialog.dismiss()
                        }
                    }
                    .setNegativeButton("不公开我的QQ",null)
                    .show()
        }

    }

    override fun showLoading() {
    }

    override fun closeLoading() {
    }

    override fun showMessage(msg: String) {
        SnackbarUtils.showShortSnackbar(rootView, msg)
    }

    override fun showUserInfo(user: User) {
        tv_user_nickname.text = user.username
        tv_user_name_kt.text = user.trueName
        iv_user_gender.setImageResource(if (user.sex == "男") R.drawable.male else R.drawable.female)
        tv_user_school.text = user.dep_name
        tv_user_num.text = user.studentKH
        tv_user_class.text = user.class_name
        tv_user_qq.text = user.qq
        tv_user_bio_kt.text = if (TextUtils.isEmpty(user.bio)) "没有签名" else user.bio
        val headPic = if (TextUtils.isEmpty(user.head_pic)) user.head_pic_thumb else user.head_pic
        if (!TextUtils.isEmpty(headPic)) {
            val width = DensityUtils.dp2px(context, 40f)
            Glide
                    .with(this)
                    .load(Constants.PICTURE_URL + headPic)
                    .override(width, width)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(iv_user_headview)
        }
    }

    override fun changeAvatarSuccess(bitmap: Bitmap) {
        iv_user_headview.setImageBitmap(bitmap)
        val intent = Intent(Constants.MainBroadcast)
        val bundle = Bundle()
        bundle.putInt("type", Constants.BROADCAST_TYPE_REFRESH_AVATAR)
        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
    }

    override fun changeAvatar() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(Constants.REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST && resultCode == RESULT_OK) {
            val uriList = Matisse.obtainResult(data)
            if (!ListUtils.isEmpty(uriList)) {
                //图片裁剪
                val uri = uriList[0]
                val cacheUri = Uri.fromFile(File(activity.cacheDir, "crop.jpg"))
                val options = UCrop.Options()
                options.setStatusBarColor(Color.parseColor("#1dcbdb"))
                options.setToolbarColor(Color.parseColor("#1dcbdb"))
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
                options.setCompressionQuality(90)
                options.setShowCropGrid(true)
                options.setFreeStyleCropEnabled(true)
                UCrop.of(uri, cacheUri)
                        .withAspectRatio(1f, 1f)
                        .withMaxResultSize(300, 300)
                        .withOptions(options)
                        .start(activity)
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            if (data != null) {
                val output = UCrop.getOutput(data)
                val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, output)
                userInfoPresenter.uploadAvatar(bitmap)
            } else {
                showMessage("出现未知错误，压缩图片出现错误")
            }
        } else {
            userInfoPresenter.showUserData()
        }
    }

    //弹出提示框
    override fun showDialog(msg: String) {
            val commonDialog = CommonDialog(context)
                commonDialog.setMessage(msg)
                        .setNegativeButton("确定", View.OnClickListener {
                                commonDialog.dismiss();
                        })
                        .show()
    }


}