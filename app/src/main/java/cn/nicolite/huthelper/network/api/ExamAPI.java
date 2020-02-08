package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.ExamResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 考试计划API
 * Created by nicolite on 17-11-2.
 */

public interface ExamAPI {
   // @GET("http://172.24.5.7/api/exam/{num}/key/{md5}")
    @GET("/api/v3/home/Examination/{num}/{appCode}")
    Observable<ExamResult> getExamData(@Path("num") String num, @Path("appCode") String appCode);
}
