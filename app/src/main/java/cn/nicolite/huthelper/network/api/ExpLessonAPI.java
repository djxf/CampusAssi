package cn.nicolite.huthelper.network.api;

import java.util.List;

import cn.nicolite.huthelper.model.bean.ExpLesson;
import cn.nicolite.huthelper.model.bean.HttpResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 实验课表API
 * Created by nicolite on 17-11-4.
 */

public interface ExpLessonAPI {
    @GET("/api/v3/Home/lessonsExp/{xh}/{code_app}")
    Observable<HttpResult<List<ExpLesson>>> getExpLesson(@Path("xh") String xh, @Path("code_app") String codeApp);
}
