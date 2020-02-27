package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.HttpPageResult;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.Say;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 说说API
 * Created by djxf 2020年1月20日
 */

public interface SayAPI {
    /**
     * 发布说说
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param content         内容
     * @param hidden          图片名称
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Statement/create/{xuehao}/{remember_code_app}")
    Observable<HttpResult<String>> createSay(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                             @Field("content") String content, @Field("hidden") String hidden,
                                             @Field("type") String type);


    /**
     * 发布评论
     *
     * @param comment  评论内容
     * @param momentId 说说Id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Statement/comment/{xuehao}/{remember_code_app}")
    Observable<HttpResult<String>> createComment(@Path("xuehao") String xuehao, @Path("remember_code_app") String appCode,
                                                 @Field("comment") String comment, @Field("moment_id") String momentId);

    /**
     * 获取指定userId的说说
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param page            页数
     * @return
     */
    @GET("/api/v3/Statement/own/{xuehao}/{remember_code_app}/{page}/{userId}")
    Observable<HttpPageResult<List<Say>>> getSayListByuserId(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                                             @Path("page") int page, @Path("userId") String userId);

    /**
     *
     * 获取所有说说
     * @param page 分页
     * @return
     */
    @GET("/api/v3/statement/statement/{studentkh}/{page}")
    Observable<HttpPageResult<List<Say>>> getSayList(@Path("studentkh")String studentkh,
                                                     @Path("page") int page);


    /**
     * 说说点赞
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param sayId           说说Id
     * @return
     */
    @GET("/api/v3/Statement/like/{xuehao}/{remember_code_app}/{sayId}")
    Observable<HttpResult<String>> likeSay(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                           @Path("sayId") String sayId);

    /**
     * 不喜欢该说说
     *
     * @param xuehao
     * @param rememberCodeApp
     * @param sayId
     * @return
     */
    @GET("/api/v3/statement/dislike/{xuehao}/{remember_code_app}/{sayId}")
    Observable<HttpResult<String>> disLikeSay(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                          @Path("sayId") String sayId);



    /**
     * 删除说说
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param sayId           说说Id
     * @return
     */
    @GET("/api/v3/Statement/delete/{xuehao}/{remember_code_app}/{sayId}")
    Observable<HttpResult<String>> deleteSay(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                             @Path("sayId") String sayId);

    /**
     * 获取自己点赞的说说
     *
     * @param num
     * @param code
     * @return
     */
    @GET("/api/v1/moments/like/{num}/{code}")
    Observable<HttpResult<List<String>>> getLikedSay(@Path("num") String num, @Path("code") String code);

    /**
     * 删除评论
     *
     * @param num
     * @param code
     * @param commentId
     * @return
     */
    @GET("/api/v3/statement/deleteC/{xuehao}/{remember_code_app}/{commentId}")
    Observable<HttpResult<String>> deleteComment(@Path("xuehao") String num, @Path("remember_code_app") String code,
                                                 @Path("commentId") String commentId);

    /**
     * 热门说说
     * @param studentKH
     */
    @GET("/api/v3/statement/fire/{studentKH}/7/{page}")
    Observable<HttpPageResult<List<Say>>> getHotSayList(@Path("studentKH") String studentKH,@Path("page") int page);

    /**
     * 我的互动
     */
    @GET("/api/v3/statement/interactive/{studentKH}/{remember_code_app}/{page}")
    Observable<HttpPageResult<List<Say>>> getMyTalkSayList(@Path("studentKH") String studentKH,
                                                           @Path("remember_code_app") String code,
                                                           @Path("page") int page);

    /**
     * 搜索说说
     * @param studentKH
     * @param code
     * @param pkey_word
     * @return
     */
    @GET("/api/v3/statement/search/{studentKH}/{remember_code_app}/{key_word}/{page}")
    Observable<HttpPageResult<List<Say>>> getSearchSayList(@Path("studentKH") String studentKH,
                                                           @Path("remember_code_app") String code,
                                                           @Path("key_word") String pkey_word,
                                                            @Path("page") int page);

    /**
     * 举报说说api
     * @param studentKH
     * @param code
     * @param content
     * @return
     */
    @GET("/api/v3/statement/report/{studentKH}/{remember_code_app}/{sayId}/123")
    Observable<HttpPageResult<String>> reportSay(@Path("studentKH")String studentKH,
                                                 @Path("remember_code_app")String code,
                                                 @Field("content")String content);
}
