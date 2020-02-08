package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.UploadImages;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 上传API接口
 * Created by nicolite on 17-10-28.
 */

public interface UploadAPI {


    /**
     * 上传图片接口
     *
     * @param studentKH       学号
     * @param rememberCodeApp cede
     * @param env             sha1(学号+remember_code_app+年月)，例如 sha1(学号+remember_code_app+年月)
     * @param type            类型  0 => 说说, 1 => 二手, 2 => 失物, 3 => 个人头像
     * @param file            文件
     * @return
     */
    @Multipart
    @POST(Constants.PICTURE_URL + "/api/v3/Upload/images/{studentKH}/{remember_code_app}/{env}/{type}")
    Observable<UploadImages> uploadImages(@Path("studentKH") String studentKH,
                                          @Path("remember_code_app") String rememberCodeApp,
                                          @Path("env") String env,
                                          @Path("type") int type,
                                          @Part MultipartBody.Part file);

    /**
     * 修改头像
     *
     * @param num  学号
     * @param code 校验码
     * @param file 头像文件
     * @return
     */
    @Deprecated
    @Multipart
    @POST(Constants.PICTURE_URL + "/api/v3/Upload/images/{num}/{remember_code_app}/{env}/3")
    Observable<HttpResult<String>> uploadAvatar(@Path("num") String num, @Path("remember_code_app") String code,
                                                @Part MultipartBody.Part file);


    /**
     * 上传二手图片
     *
     * @param file 图片文件
     * @return
     */
    @Deprecated
    @Multipart
    @POST(Constants.PICTURE_URL + "/api/v3/trade/upload")
    Observable<HttpResult<String>> uploadImg(@Part MultipartBody file);

    /**
     * 上传说说图片
     *
     * @param file 图片文件
     * @return
     */
    @Deprecated
    @Multipart
    @POST(Constants.PICTURE_URL + " /api/v3/Statement/upload")
    Observable<HttpResult<String>> uploadImg(@Part MultipartBody.Part file);
}
