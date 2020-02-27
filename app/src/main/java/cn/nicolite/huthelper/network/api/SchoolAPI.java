package cn.nicolite.huthelper.network.api;


import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.SlidePic;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 返校时间 最后使用时间 轮播图 API
 * Created by djxf on 18-11-2.
 */
public interface SchoolAPI {
    /**
     * @param studentKH          学号
     * @param rememberCodeApp   认证token
     * @return
     */
    @GET("api/v3/get/version/{studentKH}/{remember_code_app}/1")
    Call<SlidePic> slideShow(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp);

    /**
     * @param studentKH          学号
     * @param rememberCodeApp   认证token
     * @return
     */
    @GET("api/v3/get/version/{studentKH}/{remember_code_app}/1")
    Observable<SlidePic> getVersion(@Path("studentKH") String studentKH, @Path("remember_code_app") String rememberCodeApp);

}
