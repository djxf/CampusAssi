package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.Vote;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 投票API
 * Created by nicolite on 17-11-1.
 */

public interface VoteAPI {
    /**
     * 获取统计数据
     * @param studentkh
     * @param rememberCodeApp
     * @return
     */
    @GET("/api/v1/set/schema/{studentkh}/{remember_code_app}/1")
    Observable<Vote> getAirConditionerData(@Path("studentkh") String studentkh, @Path("remember_code_app") String rememberCodeApp);

    /**
     * 提交统计数据
     * @param studentkh
     * @param rememberCodeApp
     * @param opt 1 开了 2没开
     * @return
     */
    @GET("/api/v1/set/schema/{studentkh}/{remember_code_app}/1/{opt}")
    Observable<Vote> setAirConditionerData(@Path("studentkh") String studentkh, @Path("remember_code_app") String rememberCodeApp,
                                                     @Path("opt") String opt);

}
