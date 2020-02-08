package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.HttpPageResult;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.LostAndFound;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 失误招领API接口
 * Created by nicolite on 17-11-12.
 */

public interface LostAndFoundAPI {
    /**
     * 创建失物招领
     *
     * @param studentKH
     * @param rememberCodeApp
     * @param title           标题
     * @param locate          地点
     * @param time            时间
     * @param content         详细内容
     * @param hidden          图片
     * @param phone           电话
     * @param type            类型 1 招领  2 寻物
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Loses/create/{studentKH}/{remember_code_app}")
    Observable<HttpResult<String>> createLostAndFound(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp,
                                                      @Field("tit") String title, @Field("locate") String locate,
                                                      @Field("time") String time, @Field("content") String content,
                                                      @Field("hidden") String hidden, @Field("phone") String phone,
                                                      @Field("type") int type);

    /**
     * 获取失物招领列表
     *
     * @param page 页数：默认为1。
     * @param type 类型：默认为0, 代表全部。1为招领，2为遗失。
     * @return
     */
    @GET("/api/v3/Loses/goods/{page}/{type}")
    Observable<HttpPageResult<List<LostAndFound>>> getLostAndFoundList(@Path("page") int page, @Path("type") int type);


    /**
     * 根据userId获取失误招领列表
     *
     * @param studentKH
     * @param rememberCodeApp
     * @param page            页数：默认为1。
     * @param userId          用户id：默认为空，表示当前学号的失物
     * @return
     */
    @GET("/api/v3/Loses/own/{studentKH}/{remember_code_app}/{page}/{userId}")
    Observable<HttpPageResult<List<LostAndFound>>> getLostAndFoundListByUserId(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp,
                                                                               @Path("page") int page, @Path("userId") String userId);

    /**
     * 搜索失物招领
     *
     * @param studentKH       学号
     * @param rememberCodeApp code
     * @param page            页数
     * @param like            查询内容
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/Loses/search/{studentKH}/{remember_code_app}/{page}")
    Observable<HttpPageResult<List<LostAndFound>>> searchLostAndFound(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp,
                                                                      @Path("page") int page, @Field("like") String like);

    /**
     * 删除失误招领
     *
     * @param studentKH
     * @param rememberCodeApp
     * @param lostId          失物招领Id
     * @return
     */
    @GET("/api/v3/Loses/delete/{studentKH}/{remember_code_app}/{lostId}")
    Observable<HttpResult<String>> deleteLostAndFound(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp,
                                                      @Path("lostId") String lostId);
}
