package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.TimeAxis;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 主页时间轴
 * Created by nicolite on 17-7-16.
 */

public interface TimeAxisAPI {
    @GET("/api/v3/Get/calendar")
    Observable<List<TimeAxis>> getTimeAxis();
}
