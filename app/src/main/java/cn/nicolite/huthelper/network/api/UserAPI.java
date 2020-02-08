package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.ClassTimeTable;
import cn.nicolite.huthelper.model.bean.Code;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.QQResult;
import cn.nicolite.huthelper.model.bean.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 用户相关API
 * Created by nicolite on 17-10-17.
 */

public interface UserAPI {
    /**
     * 修改昵称
     *
     * @param num
     * @param code
     * @param name 用户名
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Set/profile/{num}/{code}")
    Observable<HttpResult> changeUsername(@Path("num") String num, @Path("code") String code,
                                          @Field("username") String name);

    /**
     * 修改签名
     *
     * @param num
     * @param code
     * @param bio  签名
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Set/profile/{num}/{code}")
    Observable<HttpResult> changeBio(@Path("num") String num, @Path("code") String code,
                                     @Field("bio") String bio);

    /**
     * 传入id，返回昵称，真实姓名，头像
     *
     * @return {"code":true,"data":{"username":"NICOLITE","TrueName":"陈林祥","head_pic":"\/uploads\/headpics\/201708\/1502264187.076821247.png","head_pic_thumb":"\/uploads\/headpics\/201708\/1502264187.076821247_thumb.png"}}
     */
    @GET("/api/v3/Im/get_studentinfo/{stuentKH}/{appCode}/{userId}")
    Observable<HttpResult<User>> getStudentInfo(@Path("stuentKH") String studentKH, @Path("appCode") String appCode,
                                                @Path("userId") String userId);

    /**
     * 传入姓名，返回id，头像，学院，班级
     * api/v3/Im/get_students/学号/remember_code_app/加密摘要
     * env：sha1(学号+remember_code_app+年月)
     * 示例: sha1(15408500231316568a86fa775de8909950dbcfb2495b9414d922017-10)
     * 学号 15408500231
     * 时间 2017-10
     * Post传值：
     * name ：姓名
     *
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Im/get_students/{studentKH}/{appCode}/{env}")
    Observable<HttpResult<List<User>>> getStudents(@Path("studentKH") String studentKH, @Path("appCode") String appCode,
                                                   @Path("env") String env, @Field("name") String name);

    /**
     * 获取用户信息
     *
     * @param studentkh       学号
     * @param rememberCodeApp
     * @param userId          id
     * @param env             sha1加密
     *                        加密摘要：sha1(学号+remember_code_app+用户id+年月)
     *                        示例：sha1(15408500231zxc453gfd789y4ds23s56250652017-10)
     *                        学号：15408500231
     *                        Remember_code_app : zxc453gfd789y4ds23s56
     *                        用户id : 25065
     *                        年月  ：2017-10
     * @return
     */
    @GET("api/v3/Set/user_info/{studentkh}/{remember_code_app}/{userId}/{env}")
    Observable<HttpResult<User>> getUserInfo(@Path("studentkh") String studentkh,
                                             @Path("remember_code_app") String rememberCodeApp,
                                             @Path("userId") String userId,
                                             @Path("env") String env);

    /**
     * 修改班级和学院
     * @param studentKh
     * @param remember_code_app
     * @param class_name
     * @return
     */
    @FormUrlEncoded
    @POST("api/v3/set/profile/{studentKh}/{remember_code_app}")
    Observable<Code> changeCollegeAndClass(@Path("studentKh")String studentKh,
                                           @Path("remember_code_app")String remember_code_app,
                                           @Field("class_name")String class_name
    );

    @FormUrlEncoded
    @POST("http://huthelper.cn:8080/api/v3/repair/qq/{studentKh}/{remember_code_app}")
    Observable<QQResult> changeQq(@Path("studentKh")String studentKh,
                                  @Path("remember_code_app")String remember_code_app,
                                  @Field("qq")String qq
                                  );


    /**
     *
     * 得到所有班级
     *
     */
    @GET("/api/v3/get/classes/1/1")
    Observable<ClassTimeTable> geClassSyllabus();
}
