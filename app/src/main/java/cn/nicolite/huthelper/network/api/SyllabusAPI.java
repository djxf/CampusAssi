package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.ClassTimeTable;
import cn.nicolite.huthelper.model.bean.SyllabusResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nicolite on 17-12-2.
 * 课程表API
 */

public interface SyllabusAPI {
    @GET("api/v3/Get/schedule/{studentKH}/{remember_code_app}")
    Observable<SyllabusResult> getSyllabus(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp);

    /**
     * 班级课表
     * @param studentKH 学号
     * @param rememberCodeApp
     * @param
     */
    @GET("api/v3/get/class_lessons/{studentKH}/{remember_code_app}/{class_url}")
    Observable<SyllabusResult> getAllClassSyllabus(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp,
                                                                    @Path("class_url") String class_url);
}
