package cn.nicolite.huthelper.view.iview;

import java.util.List;

import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.bean.Say;

/**
 * Created by nicolite on 17-11-14.
 */

public interface ISayView extends IBaseView {

    void showSayList(List<Say> list);//展示说说

    void showHotSayList(List<Say> list);//热门说说

    void showTalkSayList(List<Say> list);//互动说说

    void loadMore(List<Say> list);//加载更多

    void noMoreData();//没有更多数据

    void deleteSaySuccess(int position, String sayId);//删除说说成功

    void deleteCommentSuccess(int sayPosition, String commentId, int commentPosition);//删除评论成功

    void commentSuccess(String comment, int position, String userId, String username);//评论成功

    void loadFailure();//加载失败

    void loadMoreFailure();//加载更多失败
}
