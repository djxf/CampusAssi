package cn.nicolite.huthelper.view.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import cn.nicolite.huthelper.R
import cn.nicolite.huthelper.kBase.BaseActivity
import cn.nicolite.huthelper.utils.*
import cn.nicolite.huthelper.view.adapter.ShowImageAdapter
import kotlinx.android.synthetic.main.activity_show_image.*
import kotlinx.android.synthetic.main.activity_show_image.view.*

/**
 * Created by nicolite on 17-10-19.
 */

class ShowImageActivity : BaseActivity() {
    private var images: ArrayList<String> = ArrayList()
    private var currentPosition: Int = 0

    override fun initConfig(savedInstanceState: Bundle?) {
        setImmersiveStatusBar()
        setLayoutNoLimits()
        setSlideExit()
    }

    override fun initBundleData(bundle: Bundle?) {
        if (bundle != null) {
            images = bundle.getStringArrayList("images")
            currentPosition = bundle.getInt("curr", 0)
        } else {
            ToastUtils.showToastShort("获取数据出错")
            finish()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_show_image
    }

    override fun doBusiness() {
        toolbar_title.text = "${currentPosition + 1} /  ${images.size}"
        toolbar_back.setOnClickListener { finish() }
        toolbar_download.setOnClickListener {

            //用户点击按钮 响应用户
            ToastUtils.showToastShort("正在下载中...")

            if (!ButtonUtils.isFastDoubleClick()) {
                CommUtil.downloadBitmap(context, images[currentPosition])
            } else {
                SnackbarUtils.showShortSnackbar(rootView, "你点的太快了！")
            }
        }

        //标题点击后 整个顶部消失 防止遮挡图片
        toolbar_title.setOnClickListener {
            toolbar.visibility = View.INVISIBLE
        }
        viewpager.apply {
            adapter = ShowImageAdapter(context, images)
            currentItem = currentPosition

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    toolbar.visibility = View.VISIBLE
                    toolbar_title.text = "${position + 1} /  ${images.size}"
                    currentPosition = position
                }

                override fun onPageSelected(position: Int) {

                }
            })
        }
    }
}
