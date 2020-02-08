package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.Goods;
import cn.nicolite.huthelper.model.bean.GoodsItem;
import cn.nicolite.huthelper.model.bean.HttpPageResult;
import cn.nicolite.huthelper.model.bean.HttpResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 二手市场API
 * Created by nicolite on 17-10-13.
 */

public interface MarketAPI {
    /**
     * 发布商品
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param type            1出售， 2求购
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v3/trade/create/{studentKH}/{remember_code_app}")
    Observable<HttpResult<String>> createGoods(@Path("studentKH") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                               @Field("tit") String title, @Field("content") String content,
                                               @Field("prize") String price, @Field("attr") int attr,
                                               @Field("phone") String phone, @Field("address") String address,
                                               @Field("type") int type, @Field("hidden") String hidden);


    /**
     * 获取商品
     *
     * @param page 页数
     * @param type 类型 空：全部 1：出售 2：求购
     * @return
     */
    @GET("/api/v3/trade/goods/{page}/{type}")
    Observable<HttpPageResult<List<Goods>>> getGoodsList(@Path("page") int page, @Path("type") String type);

    /**
     * 获取指定学号的商品
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param page            页数
     * @param userId          用户Id 为空返回当前用户的商品
     * @return
     */
    @GET("/api/v3/trade/own/{xuehao}/{remember_code_app}/{page}/{userId}")
    Observable<HttpPageResult<List<Goods>>> getGoodsListByUserId(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                                                 @Path("page") int page, @Path("userId") String userId);

    /**
     * 获取商品详情
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param goodsId         商品Id
     * @return
     */
    @GET("/api/v3/trade/details/{xuehao}/{remember_code_app}/{goodsId}")
    Observable<HttpPageResult<GoodsItem>> getGoodsInfo(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                                       @Path("goodsId") String goodsId);

    /**
     * 删除商品
     *
     * @param xuehao          学号
     * @param rememberCodeApp
     * @param goodsId         商品Id
     * @return
     */
    @GET("/api/v3/trade/delete/{xuehao}/{remember_code_app}/{goodsId}")
    Observable<HttpResult<String>> deleteGoods(@Path("xuehao") String xuehao, @Path("remember_code_app") String rememberCodeApp,
                                               @Path("goodsId") String goodsId);


    /**
     * 模糊查询
     *
     * @param studentKH       学号
     * @param rememberCodeApp code
     * @param page            页数
     * @param like            查询内容
     * @return
     */
    @FormUrlEncoded
    @POST("api/v3/trade/search/{studentKH}/{remember_code_app}/{page}")
    Observable<HttpPageResult<List<Goods>>> searchGoods(@Path("studentKH") String studentKH,
                                                        @Path("remember_code_app") String rememberCodeApp,
                                                        @Path("page") int page, @Field("like") String like);


}
