package cn.nicolite.huthelper.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.model.bean.HttpPageResult;
import cn.nicolite.huthelper.model.bean.HttpResult;
import cn.nicolite.huthelper.model.bean.Say;
import cn.nicolite.huthelper.model.bean.SayLikedCache;
import cn.nicolite.huthelper.model.bean.User;
import cn.nicolite.huthelper.network.APIUtils;
import cn.nicolite.huthelper.network.exception.ExceptionEngine;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.fragment.SayFragment;
import cn.nicolite.huthelper.view.iview.ISayView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Change by djxf on 2020年1月20日
 */

public class SayPresenter extends BasePresenter<ISayView, SayFragment> {

    public SayPresenter(ISayView view, SayFragment activity) {
        super(view, activity);
    }

    //Manual 手动的
    public void showSayList(int type, boolean isManual) {
        switch (type) {
            case SayFragment.ALLSAY:
                loadSayList(1, isManual, false);
                break;
            case SayFragment.HOTSAY:
                loadHotSay(1, false);
                break;
            case SayFragment.MYSAY:
                //null  我的说说
                break;
            case SayFragment.SEARCHSAY:
                //搜索说说
                //loadSearchSay(1,true,);
                break;

        }
    }

    /**
     * 加载更多
     *
     * @param type
     * @param page
     */
    public void loadMore(int type, int page, String key_word) {
        switch (type) {
            case SayFragment.ALLSAY:
                loadSayList(page, true, true);
                break;
            case SayFragment.MYSAY:

                break;
            case SayFragment.HOTSAY:
                //默认加载一页 不进行加载更多
                break;
            case SayFragment.SEARCHSAY:
                //加载更多搜索说说
                loadSearchSay(page, true, key_word);
                break;
        }
    }

    /**
     * 删除说说
     *
     * @param position
     * @param sayId
     */
    public void deleteSay(final int position, final String sayId) {

        if (getView() != null) {
            getView().showMessage("正在删除！");
        }
        APIUtils.INSTANCE
                .getSayAPI()
                .deleteSay(configure.getStudentKH(), configure.getAppRememberCode(), sayId)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            getView().deleteSaySuccess(position, sayId);
                            getView().showMessage("删除成功！");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("删除失败，" + ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 删除评论
     *
     * @param sayPosition
     * @param commentId
     * @param commentPosition
     */
    public void deleteComment(final int sayPosition, final String commentId, final int commentPosition) {
        APIUtils
                .INSTANCE
                .getSayAPI()
                .deleteComment(configure.getStudentKH(), configure.getAppRememberCode(), commentId)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            getView().deleteCommentSuccess(sayPosition, commentId, commentPosition);
                            getView().showMessage("删除成功！");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("删除失败，" + ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 点赞说说
     *
     * @param sayId
     */
    public void likeSay(String sayId) {

        APIUtils.INSTANCE
                .getSayAPI()
                .likeSay(configure.getStudentKH(), configure.getAppRememberCode(), sayId)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 不喜欢该说说
     *
     * @param sayId
     */
    public void disLikeSay(final String sayId, final int position) {
        APIUtils.INSTANCE
                .getSayAPI()
                .disLikeSay(configure.getStudentKH(), configure.getAppRememberCode(), sayId)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            getView().deleteSaySuccess(position, sayId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /**
     * 添加评论
     *
     * @param comment
     * @param sayId
     * @param position
     */
    public void addComment(final String comment, String sayId, final int position) {

        if (getView() != null) {
            getView().showMessage("正在评论...！");
        }

        APIUtils.INSTANCE
                .getSayAPI()
                .createComment(configure.getStudentKH(), configure.getAppRememberCode(), comment, sayId)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            if (stringHttpResult.getCode() == 200) {
                                User user = configure.getUser();
                                if (user == null) {
                                    ToastUtils.showToastLong("请重新登录后重试");
                                    return;
                                }
                                getView().commentSuccess(comment, position, userId, user.getUsername());
                                getView().showMessage("评论成功！");
                            }
                        } else {
                            getView().showMessage("评论失败！");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("评论失败，" + ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载说说列表1
     *
     * @param page
     * @param isManual
     * @param isLoadMore
     */
    public void loadSayList(final int page, final boolean isManual, final boolean isLoadMore) {

        loadLikedSay(configure.getStudentKH(), configure.getAppRememberCode(),
                new Observer<HttpResult<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (getView() != null && !isManual) {
                            getView().showLoading();
                        }
                    }

                    @Override
                    public void onNext(HttpResult<List<String>> listHttpResult) {
                        if (getView() != null) {
                            if (listHttpResult.getMsg().equals("成功获得点赞数据")) {
                                SayLikedCache.setLikedList(listHttpResult.getData());
                                loadSayList(page, isLoadMore);
                            } else {
                                getView().showMessage("加载失败!");
                                if (isLoadMore) {
                                    getView().loadMoreFailure();
                                } else {
                                    getView().loadFailure();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());
                            if (isLoadMore) {
                                getView().loadMoreFailure();
                            } else {
                                getView().loadFailure();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载说说列表
     *
     * @param page
     * @param isLoadMore
     */
    private void loadSayList(final int page, final boolean isLoadMore) {

        APIUtils.INSTANCE
                .getSayAPI()
                .getSayList(configure.getUser().getStudentKH(), page)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    if (page <= listHttpPageResult.getPageination() && listHttpPageResult.getPageination() != 1) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                    getView().showMessage("暂时没有相关内容！");
                                }


                                getView().showSayList(listHttpPageResult.getData());
                            } else {
                                getView().closeLoading();
                                getView().showMessage("加载失败!");
                                if (isLoadMore) {
                                    getView().loadMoreFailure();
                                } else {
                                    getView().loadFailure();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());
                            if (isLoadMore) {
                                getView().loadMoreFailure();
                            } else {
                                getView().loadFailure();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取自己点赞的说说
     *
     * @param studentKh
     * @param appCode
     * @param observer
     */
    public void loadLikedSay(String studentKh, String appCode, Observer<HttpResult<List<String>>> observer) {
        APIUtils.INSTANCE
                .getSayAPI()
                .getLikedSay(studentKh, appCode)
                .compose(getActivity().<HttpResult<List<String>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取指定用户的说说
     *
     * @param userId
     * @param page
     * @param isManual
     * @param isLoadMore
     */
    public void loadSayListByUserId(final String userId, final int page, final boolean isManual, final boolean isLoadMore) {

        loadLikedSay(configure.getStudentKH(), configure.getAppRememberCode(), new Observer<HttpResult<List<String>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (getView() != null && !isManual) {
                    getView().showLoading();
                }
            }

            @Override
            public void onNext(HttpResult<List<String>> listHttpResult) {
                if (getView() != null) {
                    if (listHttpResult.getMsg().equals("成功获得点赞数据")) {
                        SayLikedCache.setLikedList(listHttpResult.getData());
                        loadSayListByUserId(configure.getStudentKH(), configure.getAppRememberCode(), userId,
                                page, isLoadMore);
                    } else {
                        getView().closeLoading();
                        getView().showMessage("加载失败!");
                        if (isLoadMore) {
                            getView().loadMoreFailure();
                        } else {
                            getView().loadFailure();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());
                    if (isLoadMore) {
                        getView().loadMoreFailure();
                    } else {
                        getView().loadFailure();
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void loadSayListByUserId(String studentKH, String appCode, String userId, final int page, final boolean isLoadMore) {

        APIUtils.INSTANCE
                .getSayAPI()
                .getSayListByuserId(studentKH, appCode, page, userId)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    if (page <= listHttpPageResult.getPageination() && listHttpPageResult.getPageination() != 1) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                    getView().showMessage("暂时没有相关内容！");
                                }
                                getView().showSayList(listHttpPageResult.getData());
                            } else {
                                getView().showMessage("加载失败!");
                                if (isLoadMore) {
                                    getView().loadMoreFailure();
                                } else {
                                    getView().loadFailure();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());
                            if (isLoadMore) {
                                getView().loadMoreFailure();
                            } else {
                                getView().loadFailure();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载热门说说
     *
     * @param page
     * @param isLoadMore
     */
    public void loadHotSay(final int page, final boolean isLoadMore) {
        APIUtils.INSTANCE
                .getSayAPI()
                .getHotSayList(configure.getStudentKH(), page)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    if (page <= listHttpPageResult.getPageination()) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                getView().showHotSayList(listHttpPageResult.getData());
                                return;
                            }
                            if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                getView().showMessage("暂时没有相关内容！");
                            }
                            getView().showHotSayList(listHttpPageResult.getData());
                        } else {
                            getView().closeLoading();
                            getView().showMessage("加载失败!");

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载我的互动说说
     *
     * @param page
     * @param isLoadMore
     */
    public void loadMyTalkSay(final int page, final boolean isLoadMore) {
        APIUtils.INSTANCE
                .getSayAPI()
                .getMyTalkSayList(configure.getStudentKH(), configure.getAppRememberCode(), page)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {

                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    //刷新后会从第一页开始重新加载 如果此时pageination==1 会重复添加数据
                                    if (page <= listHttpPageResult.getPageination()) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                getView().showTalkSayList(listHttpPageResult.getData());
                                return;
                            }
                            if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                getView().showMessage("暂时没有相关内容！");
                            }
                            getView().showTalkSayList(listHttpPageResult.getData());
                        } else {
                            getView().closeLoading();
                            getView().showMessage("加载失败!");

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 搜索说说
     *
     * @param page
     * @param isLoadMore
     */
    public void loadSearchSay(final int page, final boolean isLoadMore, final String key_word) {
        APIUtils.INSTANCE
                .getSayAPI()
                .getSearchSayList(configure.getStudentKH(), configure.getAppRememberCode(), key_word, page)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    if (page <= listHttpPageResult.getPageination()) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                getView().showSearchSayList(listHttpPageResult.getData());
                                return;
                            }
                            if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                getView().showMessage("暂时没有相关内容！");
                            }
                            getView().showHotSayList(listHttpPageResult.getData());
                        } else {
                            getView().closeLoading();
                            getView().showMessage("加载失败!");

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载关注说说
     *
     * @param page
     * @param isLoadMore
     */
    public void loadFollowSay(final int page, final boolean isLoadMore) {
        APIUtils.INSTANCE
                .getSayAPI()
                .getFollowSays(configure.getStudentKH(), configure.getAppRememberCode(), page)
                .compose(getActivity().<HttpPageResult<List<Say>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpPageResult<List<Say>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpPageResult<List<Say>> listHttpPageResult) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (listHttpPageResult.getCode() == 200) {
                                if (isLoadMore) {
                                    if (listHttpPageResult.getPageination() == 0){
                                        getView().showMessage("你还没有关注的用户");
                                        return;
                                    }
                                    if (page <= listHttpPageResult.getPageination()) {
                                        getView().loadMore(listHttpPageResult.getData());
                                    } else {
                                        getView().noMoreData();
                                    }
                                    return;
                                }
                                if (listHttpPageResult.getData().size()== 0 ){
                                    getView().showMessage("你暂时没有关注的用户");
                                }
                                getView().showFollowSayList(listHttpPageResult.getData());
                                return;
                            }
                            if (ListUtils.isEmpty(listHttpPageResult.getData())) {
                                getView().showMessage("暂时没有相关内容！");
                            }
                            getView().showSayList(listHttpPageResult.getData());
                        } else {
                            getView().closeLoading();
                            getView().showMessage("加载失败!");

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage("加载失败，" + ExceptionEngine.handleException(e).getMsg());

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 关注和取消关注某用户
     *
     * @param user_id
     */
    public void follow(String user_id) {
        APIUtils.INSTANCE
                .getSayAPI()
                .followSays(configure.getStudentKH(), configure.getAppRememberCode(), user_id)
                .compose(getActivity().<HttpResult<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (getView() != null) {
                            if (stringHttpResult.getCode() == 200){
                                getView().showMessage("success");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().closeLoading();
                            getView().showMessage(ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

