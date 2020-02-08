package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 登录API接口
 * Created by nicolite on 17-10-17.
 */

public interface LoginAPI {
    /**
     * 登录，登录成功将会返回用户信息
     * 0 => 'web'，1 => 'android'，2 => 'ios'，3 => 'win'，4 => 'WeChat'
     * passwd：用rsa非对称加密后base64转码
     * 公钥：(双引号里面，注意换行与空格)
     * ”-----BEGIN PUBLIC KEY-----
     * MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ1T65TCvVK+vndaRgeE7PhLS+
     * vYdNNMTjCD1WO1eRFqBuTM5rMP7h3Hq+jsTicFh1/FoPRjwvlWhuCXZi6IqjphvV
     * vXZso74xhNLA8Lml750qkkyaDKD5Pr2PGIaJ6h4WKvt7sBhDQ6LZQcz83Disp1fr
     * gtWhPytyJr7D1I4DvwIDAQAB
     * -----END PUBLIC KEY-----”
     *
     * @param studentKH
     * @param password
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Get/login/1")
    Observable<HttpResult<User>> login2(@Field("studentkh") String studentKH, @Field("passwd") String password);

    /**
     * 登录，登录成功将会返回用户信息
     *
     * @param num      学号
     * @param password 密码
     * @return
     */
    @GET("/api/v1/get/login/{num}/{pass}")


    Observable<HttpResult<User>> login(@Path("num") String num, @Path("pass") String password);

}
