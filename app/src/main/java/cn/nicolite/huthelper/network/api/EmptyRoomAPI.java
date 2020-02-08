package cn.nicolite.huthelper.network.api;



import cn.nicolite.huthelper.model.bean.EmptyRoom;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 *  空教室查询接口
 *
 */
public interface EmptyRoomAPI {

    @GET("http://218.75.197.123:83/app.do?method=getKxJscx")
    Observable<EmptyRoom> getEmptyRoom(@Query("time") String time,
                                       @Query("idleTime") String idleTime,
                                       @Query("jxlid") String jxlid,
                                       @Header("token") String token
                                       );
}
