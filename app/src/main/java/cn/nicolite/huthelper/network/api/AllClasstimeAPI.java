package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.ClassTimeTable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AllClasstimeAPI {
    /**
     *
     * 全校课表
     * @param studentKH
     * @param rememberCodeApp
     * @return
     */
    @GET("/api/v3/get/classes/{studentKH}/{remember_code_app}")
    Call<ClassTimeTable> getClassSyllabus(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp);

}
