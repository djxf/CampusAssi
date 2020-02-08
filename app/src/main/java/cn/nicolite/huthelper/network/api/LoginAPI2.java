package cn.nicolite.huthelper.network.api;

import cn.nicolite.huthelper.model.bean.User2;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginAPI2 {

    /**
     * @param xh
     * @param pwd
     * @return
     */
    @GET("http://218.75.197.123:83/app.do?method=authUser")
    Observable<User2> getUserToken(@Query("xh") String xh,
                                   @Query("pwd") String pwd);
}
