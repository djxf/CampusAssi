package cn.nicolite.huthelper.network.api;


import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.SimpleResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 表白墙 API
 * create by djxf 2019-03-28
 *
 */
public interface ProfessAPI {

    /**
     *
     * 获取所有表白
     * @param studentKH
     * @param token
     * @return
     */
    @GET("api/v3/love/all/{studentKH}/{token}")
    Observable<HttpResult<String>> getAllLove(@Path("studentKH")String studentKH,
                                              @Path("token")String token);


    /**
     *
     * 获取个人表白
     * @param studentKH
     * @param token
     * @return
     */
    @GET("api/v3/love/own/{studentKH}/{token}")
    Observable<HttpResult<String>> getOwnLove(@Path("studentKH")String studentKH,
                                              @Path("token")String token);


    /**
     * 创建表白
     *
     * @param studentKH
     * @param token
     * @param content
     * @param hidden
     * @param type    0 表白  1 秀恩爱  2 交友
     * @return
     */
    @FormUrlEncoded
    @POST("api/v3/love/create/{studentKH}/{token}")
    Observable<SimpleResult> createLove(@Path("studentKH")String studentKH,
                                              @Path("token")String token,
                                              @Field("content")String content,
                                              @Field("hidden")String hidden,
                                              @Field("type")String type

    );

    /**
     * 删除表白
     *
     * @param studentKH
     * @param token
     * @param id    表白id
     * @return
     */
    @GET("api/v3/love/delete/{studentKH}/{token}")
    Observable<SimpleResult> deleteLove(@Path("studentKH")String studentKH,
                                              @Path("token")String token,
                                              @Field("id")String id);


    /**
     * 点赞或者取消点赞
     *
     * @param studentKH
     * @param token
     * @param id    表白id
     * @return
     */
    @GET("api/v3/love/like/{studentKH}/{token}")
    Observable<SimpleResult> likeLove(@Path("studentKH")String studentKH,
                                            @Path("token")String token,
                                            @Field("id")String id);


    /**
     * 添加评论
     *
     * @param studentKH
     * @param token
     * @param id        表白id
     * @param content  评论内容
     * @param target    评论的对象的id（选填）
     * @return
     */
    @POST("api/v3/comment/add/{studentKH}/{token}")
    Observable<SimpleResult> addComment(@Path("studentKH")String studentKH,
                                              @Path("token")String token,
                                              @Field("id")String id,
                                              @Field("content")String content,
                                              @Field("target")String target
                                              );

    /**
     * 删除评论
     *g
     * @param studentKH
     * @param token
     * @param id    评论id
     * @return
     */
    @GET("api/v3/comment/delete/{studentKH}/{token}")
    Observable<SimpleResult> deleteComment(@Path("studentKH")String studentKH,
                                           @Path("token")String token,
                                           @Field("id")String id
                                                 );

}
