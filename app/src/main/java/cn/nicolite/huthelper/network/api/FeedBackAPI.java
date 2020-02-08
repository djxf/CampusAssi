package cn.nicolite.huthelper.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 反馈API
 * Created by nicolite on 17-10-24.
 */

public interface FeedBackAPI {
    @FormUrlEncoded
    @POST("/home/msg/0")
    Observable<ResponseBody> feedBack(@Field("email") String email, @Field("content") String content);
}
