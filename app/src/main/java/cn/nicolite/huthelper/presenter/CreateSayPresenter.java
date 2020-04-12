package cn.nicolite.huthelper.presenter;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.SlidePic;
import cn.nicolite.huthelper.model.bean.UploadImages;
import cn.nicolite.huthelper.network.APIUtils;
import cn.nicolite.huthelper.network.exception.ExceptionEngine;
import cn.nicolite.huthelper.utils.CommUtil;
import cn.nicolite.huthelper.utils.EncryptUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.view.activity.CreateSayActivity;
import cn.nicolite.huthelper.view.iview.ICreateSayView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by nicolite on 17-11-15.
 */

public class CreateSayPresenter extends BasePresenter<ICreateSayView, CreateSayActivity> {
    public CreateSayPresenter(ICreateSayView view, CreateSayActivity activity) {
        super(view, activity);
    }

    public void selectImages() {
        AndPermission
                .with(getActivity())
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (getView() != null) {
                            if (requestCode == 100) {
                                getView().selectImages();
                            }
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if (getView() != null) {
                            getView().showMessage("获取权限失败，请授予文件读写权限！");
                        }
                    }
                })
                .start();
    }

    private StringBuilder stringBuilder = new StringBuilder();
    private AtomicInteger uploadCount = new AtomicInteger(0);

    /**
     *  上传图片
     *
     * @param bytes   上传的字节数组
     * @param i       现在上传的是第几个
     * @param count  总共需要上传的图片个数
     * @param type   上传图片的格式 .jpg或者.gif
     */
    private void uploadImages(byte[] bytes, final int count, final int i,final String type) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        final String date = simpleDateFormat.format(new Date());
        String env = EncryptUtils.SHA1(configure.getStudentKH() + configure.getAppRememberCode() + date);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/gif"), bytes);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", "01."+type, requestBody);



        APIUtils.INSTANCE
                .getUploadAPI()
                .uploadImages(configure.getStudentKH(), configure.getAppRememberCode(), env, 0, file)
                .compose(getActivity().<UploadImages>bindToLifecycle())
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadImages>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadImages uploadImages) {
                        if (getView() != null) {
                            if (uploadImages.getCode() == 200) {
                                uploadCount.incrementAndGet();

                                if (getView() != null) {
                                    getView().uploadProgress("正在上传图片:"+uploadCount+"/"+count);
                                }

                                stringBuilder.append("//");
                                if (!type.equals(".gif")){
                                    stringBuilder.append(uploadImages.getData());
                                }else {
                                    stringBuilder.append(uploadImages.getData_original());
                                }
                                if (getView() != null && uploadCount.get() == count) {
                                    String string = stringBuilder.toString();
                                    stringBuilder.delete(0, stringBuilder.length());
                                    uploadCount.set(0);
                                    if (!TextUtils.isEmpty(string)) {
                                        getView().uploadSayInfo(string);
                                    } else {
                                        getView().uploadFailure("获取上传图片信息失败！");
                                    }
                                }
                            } else {
                                stringBuilder.delete(0, stringBuilder.length());
                                uploadCount.set(0);
                                getView().uploadFailure("上传图片失败！"+uploadImages.getMsg());
                                getView().closeLoading();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() == null) {
                            stringBuilder.delete(0, stringBuilder.length());
                            uploadCount.set(0);
                            getView().uploadFailure("发布失败！");
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                            getView().closeLoading();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    List<File> fileList = new ArrayList<>();
    /**
     *
     * 创建说说
     * @param activity
     * @param uriList
     */
    public void createSay(Activity activity, final List<Uri> uriList){
        if (ListUtils.isEmpty(uriList)) {
            if (getView() != null) {
                getView().showMessage("未选择图片！");
            }
            return;
        }
        if (getView() != null) {
            getView().showMessage("正在发布，请勿关闭页面！");
        }
        for (int i = 0; i < uriList.size(); i++) {
            if (getView() != null) {
                getView().uploadProgress("正在压缩图片！");
            }
            Luban
                    .with(activity)
                    .load(CommUtil.uri2File(activity, uriList.get(i)))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            fileList.add(file);
                            if (!ListUtils.isEmpty(fileList) && fileList.size() == uriList.size()) {
                                for (int i = 0; i < fileList.size(); i++) {
                                    byte[] bytes;
                                    String reg = "(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG|.gif)";
                                    Matcher matcher = Pattern.compile(reg).matcher(fileList.get(i).getName());
                                    String type = ".jpg";
                                    if (matcher.find()){
                                        type = matcher.group(0);
                                    }
                                    if (!fileList.get(i).getName().endsWith("gif")){
                                        Bitmap bitmap = BitmapFactory.decodeFile(fileList.get(i).getPath());
                                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                        bytes = outputStream.toByteArray();
                                        bitmap.recycle();
                                    }else {
                                        bytes = File2byte(fileList.get(i));
                                    }
                                    uploadImages(bytes,fileList.size(), i + 1,type);
                                }
                                fileList.clear();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getView() != null) {
                                getView().uploadFailure("压缩图片出现异常！");
                                getView().showMessage("压缩失败，" + ExceptionEngine.handleException(e).getMsg());
                            }
                        }
                    }).launch();
        }

    }

    public void uploadSayInfo(String content, String hidden,String type) {

        APIUtils.INSTANCE
                .getSayAPI()
                .createSay(configure.getStudentKH(), configure.getAppRememberCode(), content, hidden,type)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (getView() != null) {
                            getView().showLoading();
                        }
                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (stringHttpResult.getCode() == 200) {
                                getView().showMessage("发布成功！");
                                getView().publishSuccess();
                            } else {
                                getView().showMessage("发布失败，" + stringHttpResult.getCode() + " msg：" + stringHttpResult.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("发布失败，" + ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     *
     * 展示选择标签的弹窗
     */
    public void selectLableView(){
        APIUtils.INSTANCE
                .getSchoolAPI()
                .getVersion(configure.getStudentKH(),configure.getAppRememberCode())
                .compose(getActivity().<SlidePic>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SlidePic>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SlidePic slidePic) {
                        if (slidePic.getCode().equals("200")){
                            if (getView()!= null){
                                getView().showSelectLableView(slidePic.getData().getMoment_types());
                            }
                        }else {
                            if (getView()!=null){
                                getView().showMessage("网络错误! 错误码"+slidePic.getCode());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            getView().showMessage(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 将文件转换成二进制流 过于耗时 应该放在子线程中操作
     *
     * @param file
     * @return
     */

    public static byte[] File2byte(File file){
        byte[] buff = null;
        long fileLength = file.length();
        String threadName = Thread.currentThread().getName();
        long time1 = System.currentTimeMillis();
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1){
                bos.write(b,0,n);
            }
            fis.close();
            bos.close();
            buff = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        LogUtils.d("TAG",threadName+":time:"+(System.currentTimeMillis()-time1)+"fileLength:"+fileLength);
            return buff;
    }


    /**
     * 测试方法 不在正式环境中使用
     *
     */
    public static void test(){
           Observable<Object>  observable =  Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter){

            }
        });


           //观察者
            Observer<Object> observer = new Observer<Object>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

            observable.subscribe(observer);

    }
}
