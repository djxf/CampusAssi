package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.Grade;
import cn.nicolite.huthelper.model.bean.GradeRankResult;
import cn.nicolite.huthelper.model.bean.HttpResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 成绩相关API
 * Created by nicolite on 17-11-12.
 */

public interface GradeAPI {

    @GET("api/v3/Get/score/{studentKH}/{remember_code_app}")
    Observable<HttpResult<List<Grade>>> getGradeList(@Path("studentKH") String studentKH, @Path("remember_code_app") String appCode);

    @GET("api/v3/Get/rank/{studentKH}/{remember_code_app}")
    Observable<GradeRankResult> getGradeRank(@Path("studentKH") String studentKH, @Path("remember_code_app") String appCode);

}
