package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.Update;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 检查更新API
 * Created by nicolite on 17-10-24.
 */

public interface UpdateAPI {
    @GET("/api/v3/Get/version/{num}/{appCode}/1")
    Observable<HttpResult<Update>> checkUpdate(@Path("num") String num, @Path("appCode") String appCode);
}
