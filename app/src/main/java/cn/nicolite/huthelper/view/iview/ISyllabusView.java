package cn.nicolite.huthelper.view.iview;

import java.util.List;

import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.bean.Lesson;

/**
 * Created by nicolite on 17-12-4.
 */

public interface ISyllabusView extends IBaseView {
    /**
     * 请求单个课程
     * @param lessonList
     */
    void showSyllabus(List<Lesson> lessonList);

    /**
     * 请求全部课程
     * @param lessonList
     */
    void showAllClassSyllabus(List<Lesson> lessonList);
}
