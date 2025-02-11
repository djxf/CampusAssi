package cn.nicolite.huthelper.presenter

import android.Manifest
import android.graphics.Bitmap
import cn.nicolite.huthelper.kBase.BasePresenter
import cn.nicolite.huthelper.model.bean.*
import cn.nicolite.huthelper.network.APIUtils
import cn.nicolite.huthelper.network.exception.ExceptionEngine
import cn.nicolite.huthelper.utils.EncryptUtils
import cn.nicolite.huthelper.utils.LogUtils
import cn.nicolite.huthelper.view.fragment.UserInfoFragment
import cn.nicolite.huthelper.view.iview.IUserInfoView
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * UserInfoPresenter
 * Created by djxf on 17-10-28.
 */

class UserInfoPresenter(iView: IUserInfoView, view: UserInfoFragment) : BasePresenter<IUserInfoView, UserInfoFragment>(iView, view) {

    fun showUserData() {

        val user = configure.user
        if (user == null) {
            getIView()?.showMessage("获取当前登录用户失败，请重新登录！")
        } else {
            getIView()?.showUserInfo(user)
        }
    }

    fun uploadAvatar(bitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val bytes = outputStream.toByteArray()
        val requestBody = RequestBody.create(MediaType.parse("img/jpeg"), bytes)
        val file = MultipartBody.Part.createFormData("file", "01.jpg", requestBody)

        getIView()?.showMessage("头像上传中！")

        val simpleDateFormat = SimpleDateFormat("yyyy-MM", Locale.CHINA)
        val date = simpleDateFormat.format(Date())
        val env = EncryptUtils.SHA1(configure.studentKH + configure.appRememberCode + date)

        APIUtils
                .getUploadAPI()
                .uploadImages(configure.studentKH, configure.appRememberCode, env, 3, file)
                .compose(getView()?.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<UploadImages> {
                    override fun onSubscribe(d: Disposable) {
                        getIView()?.showLoading()
                    }

                    override fun onNext(uploadImages: UploadImages) {
                        getIView()?.closeLoading()
                        if (uploadImages.code == 200) {
                            getIView()?.showMessage("修改成功!")
                            val user = configure.user
                            user.head_pic_thumb = uploadImages.data
                            user.head_pic = uploadImages.data_original
                            daoSession.userDao.update(user)
                            getIView()?.changeAvatarSuccess(bitmap)
                        } else {
                            getIView()?.showMessage("修改失败!")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getIView()?.closeLoading()
                        getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                    }

                    override fun onComplete() {

                    }
                })

    }

    fun changAvatar() {
        AndPermission
                .with(fragment!!)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callback(object : PermissionListener {
                    override fun onSucceed(requestCode: Int, grantPermissions: List<String>) {
                        if (requestCode == 100) {
                            getIView()?.changeAvatar()
                        }
                    }

                    override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
                        getIView()?.showMessage("获取权限失败，请授予文件读写权限！")
                    }
                })
                .start()
    }

    fun changeUserName(userName: String) {

        getIView()?.showMessage("昵称修改中！")

        APIUtils
                .getUserAPI()
                .changeUsername(configure.studentKH, configure.appRememberCode, userName)
                .compose(getView()?.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HttpResult<*>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(httpResult: HttpResult<*>) {
                        if (httpResult.code == 200) {
                            val userDao = daoSession.userDao
                            val user = configure.user
                            user.username = userName
                            userDao.update(user)
                            getIView()?.showMessage("修改成功!")
                        } else {
                            getIView()?.showMessage("修改失败，code： ${httpResult.code}，${httpResult.msg}")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                    }

                    override fun onComplete() {

                    }
                })
    }

    //返回班级
    fun getAllClass(){


        APIUtils
                .getUserAPI()
                .geClassSyllabus()
                .compose(getView()?.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ClassTimeTable> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(classtimetable: ClassTimeTable) {
                        if (classtimetable.code == 200) {
                                getIView()?.getAllClass(classtimetable)
                        } else {
                            getIView()?.showMessage("修改失败，code： ${classtimetable.code}")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                    }

                    override fun onComplete() {

                    }
                })
    }


    //更换个人签名
    fun changeBio(bio: String) {

        APIUtils
                .getUserAPI()
                .changeBio(configure.studentKH, configure.appRememberCode,bio)
                .compose(getView()?.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HttpResult<*>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(httpResult: HttpResult<*>) {
                        if (httpResult.code == 200) {
                            val userDao = daoSession.userDao
                            val user = configure.user
                            user.bio = bio
                            userDao.update(user)
                            getIView()?.showMessage("修改成功!")
                        } else {
                            getIView()?.showMessage("修改失败，${httpResult.code}，${httpResult.msg}")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                    }

                    override fun onComplete() {

                    }
                })
    }

    /**
     * class 关键字冲突 用_class 代替
     */
    fun changeCollegeAndClass(_class:String,college : String){
        APIUtils
                .getUserAPI()
                .changeCollegeAndClass(configure.studentKH,configureList.get(0).appRememberCode,_class)
                .compose(getView()?.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Code> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(_code: Code) {
                        LogUtils.d("TAG",_code.code.toString())
                        if (_code.code == 200) {
                            val userDao = daoSession.userDao
                            val user = configure.user
                            user.class_name=_class
                            user.dep_name=college
                            userDao.update(user)
                            getIView()?.showMessage("修改成功!")
                            getIView()?.showUserInfo(user)
                        } else {
                            getView()?.showDialog("修改失败,"+_code.code)
                        }
                    }

                    override fun onError(e: Throwable) {
                        getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                    }

                    override fun onComplete() {

                    }
                })
    }

    /**
     * 修改qq
     */
    fun changeQq(qq : String){
            APIUtils
                    .getUserAPI()
                    .changeQq(configure.studentKH,configure.appRememberCode,qq)
                    .compose(getView()?.bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<QQResult> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onNext(qqResult: QQResult) {
                                getView()?.closeLoading()
                                if (qqResult.code == 200){
                                    getView()?.showMessage("修改成功")
                                    val userDao = daoSession.userDao
                                    val user = configure.user
                                    user.qq = qqResult.data.qq
                                    userDao.update(user)
                                    getView()?.showUserInfo(user)
                                }else{
                                    getView()?.showMessage("修改失败！"+qqResult.code)
                                }
                        }

                        override fun onError(e: Throwable) {
                            getIView()?.showMessage(ExceptionEngine.handleException(e).msg)
                        }

                        override fun onComplete() {

                        }
                    })
    }
}
