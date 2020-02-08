package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.Electric;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 电费API接口
 * Created by nicolite on 17-10-31.
 * Observable Rxjava形式
 */

public interface ElectricAPI {
    @GET("/api/v3/get/power_e/{part}/{buildingNo}/{roomNo}/{xh}/{rememberCode}/{enc}")
    Observable<Electric> getElectric(@Path("part")String part,@Path("buildingNo") String buildingNo, @Path("roomNo") String roomNo,
                                     @Path("xh") String xh, @Path("rememberCode") String rememberCode,
                                     @Path("enc") String enc);
}
