package cn.nicolite.huthelper.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import butterknife.BindView;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseFragment;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Say;
import cn.nicolite.huthelper.presenter.SayPresenter;
import cn.nicolite.huthelper.utils.ButtonUtils;
import cn.nicolite.huthelper.utils.CommUtil;
import cn.nicolite.huthelper.utils.DensityUtils;
import cn.nicolite.huthelper.utils.KeyBoardUtils;

import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.ScreenUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.activity.FeedBackActivity;
import cn.nicolite.huthelper.view.activity.SayActivity;
import cn.nicolite.huthelper.view.activity.ShowImageActivity;
import cn.nicolite.huthelper.view.activity.UserInfoCardActivity;
import cn.nicolite.huthelper.view.adapter.SayAdapter;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.iview.ISayView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by djxf on 19-05-15
 */

public class SayFragment extends BaseFragment implements ISayView {
    @BindView(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;
    @BindView(R.id.rootView)
    LinearLayout rootView;

    private int type = ALLSAY;
    private String searchText = "";
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private   List<Say> sayList = new ArrayList<>();
    public static final int ALLSAY = 0;//所有说说
    public static final int MYSAY = 1;//我的说说
    public static final int HOTSAY = 2;//热门说说
    public static final int MYTALK = 3;//我的互动
    public static final int SEARCHSAY = 4; //搜索说说
    public static final int FOLLOW = 5; //关注说说
    private SayPresenter sayPresenter;
    private boolean isNoMore = false;
    private int currentPage = 1;
    private SayAdapter sayAdapter;


    public static SayFragment newInstance(int type, String searchText) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        if (!TextUtils.isEmpty(searchText)) {
            args.putString("searchText", searchText);
        }
        SayFragment fragment = new SayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle arguments) {
        if (arguments != null) {
            type = arguments.getInt("type", ALLSAY);
            if (type == MYSAY ) {
                searchText = arguments.getString("searchText", "");
            } else if (type == HOTSAY) {
                searchText = arguments.getString("searchText", "");
            } else if (type == MYTALK) {
                searchText = arguments.getString("searchText", "");
            }else if (type == SEARCHSAY){
                searchText = arguments.getString("searchText","");
            }else  if (type == FOLLOW){
                searchText = arguments.getString("searchText","");
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_say;
    }

    @Override
    protected void doBusiness() {


        lRecyclerView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        sayAdapter = new SayAdapter(context, sayList,searchText);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(sayAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        sayPresenter = new SayPresenter(this, this);

        //内部接口
        sayAdapter.setOnItemClickListener(new SayAdapter.OnItemClickListener() {

            @Override
            public void onAddCommentClick(int position, String sayId,String username) {
                showCommitView(position, sayId,username);
            }


            @Override
            public void onUserClick(String userId, String username) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                bundle.putString("username", username);
                startActivity(UserInfoCardActivity.class, bundle);
            }

            @Override
            public void onLikeClick(String sayId) {
                sayPresenter.likeSay(sayId);
            }

            @Override
            public void onDeleteClick(final String sayId, final int position) {
                final CommonDialog commonDialog = new CommonDialog(context);
                commonDialog
                        .setMessage("确定删除这条说说？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonDialog.dismiss();
                                sayPresenter.deleteSay(position, sayId);//防止越界
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }


            @Override
            public void onImageClick(int position, List<String> urlList) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", (ArrayList<String>) urlList);
                bundle.putInt("curr", position);
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

            @Override
            public void onCommentDeleteClick(final int sayPosition, final String commentId, final int commentPosition) {
                final CommonDialog commonDialog = new CommonDialog(context);
                commonDialog.setMessage("确认删除?")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonDialog.dismiss();
                                sayPresenter.deleteComment(sayPosition, commentId, commentPosition);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }

            //兼容有问题
            @Override
            public void onArrowDownClick(ImageView imageView,String sayId,int position) {
                //点击向下箭头 弹框提示
                showSaySetMenu(imageView,sayId,position);
            }
        });


        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                switch (type) {
                    case SayFragment.ALLSAY:
                        sayPresenter.showSayList(type, true);
                        break;
                    case SayFragment.MYSAY:
                        sayPresenter.loadSayListByUserId(searchText, 1, true, false);
                        break;
                    case SayFragment.HOTSAY:
                        sayPresenter.loadHotSay(1, true);
                        break;
                    case SayFragment.MYTALK:
                        sayPresenter.loadMyTalkSay(1, true);
                        break;
                    case SayFragment.SEARCHSAY:
                        //搜索说说
                        sayPresenter.loadSearchSay(1,false,searchText);
                        break;
                    case SayFragment.FOLLOW:
                        //关注说说
                        sayPresenter.loadFollowSay(1,false);
                        break;
                }
            }
        });

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isNoMore) {
                    switch (type) {
                        case SayFragment.ALLSAY:
                            sayPresenter.loadMore(type, ++currentPage,"");
                            break;
                        case SayFragment.MYSAY:
                            sayPresenter.loadSayListByUserId(searchText, ++currentPage, true, true);
                            break;
                        case SayFragment.HOTSAY:
                            sayPresenter.loadHotSay(++currentPage, true);
                            break;
                        case SayFragment.MYTALK:
                            sayPresenter.loadMyTalkSay(++currentPage, true);
                            break;
                        case SayFragment.SEARCHSAY:
                            sayPresenter.loadSearchSay(++currentPage,false,searchText);
                            break;
                        case SayFragment.FOLLOW:
                            sayPresenter.loadFollowSay(++currentPage,true);
                            break;
                    }
                }
            }
        });

        lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                if (!isNoMore) {
                    switch (type) {
                        case SayFragment.ALLSAY:
                            sayPresenter.loadMore(type, currentPage,"");
                            break;
                        case SayFragment.MYSAY:
                            sayPresenter.loadSayListByUserId(searchText, currentPage, true, true);
                            break;
                        case SayFragment.SEARCHSAY:
                            sayPresenter.loadSearchSay(currentPage,true,searchText);
                            break;
                        case SayFragment.FOLLOW:
                            sayPresenter.loadFollowSay(currentPage,true);
                            break;
                    }
                }
            }
        });

        lRecyclerView.forceToRefresh();

    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showMessage(String msg) {
        SnackbarUtils.showShortSnackbar(rootView, msg);
    }

    @Override
    public void showSayList(List<Say> list) {
        lRecyclerView.setNoMore(false);
        sayList.clear();
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHotSayList(List<Say> list) {
        sayList.clear();
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTalkSayList(List<Say> list) {
        sayList.clear();
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
    }

    @Override
    public void showSearchSayList(List<Say> list) {
        sayList.clear();
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFollowSayList(List<Say> list) {
        sayList.clear();
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Say> list) {
        sayList.addAll(list);
        lRecyclerView.refreshComplete(list.size());
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void noMoreData() {
        --currentPage;
        isNoMore = true;
        lRecyclerView.setNoMore(true);
    }

    @Override
    public void deleteSaySuccess(int position, String sayId) {
        Say say = sayList.remove(position-1);
        lRecyclerViewAdapter.notifyItemRemoved(position);

    }

    @Override
    public void deleteCommentSuccess(int sayPosition, String commentId, int commentPosition) {
        sayList.get(sayPosition).getComments().remove(commentPosition);
        sayAdapter.notifyItemChanged(sayPosition);
    }

    @Override
    public void commentSuccess(String comment, int position, String userId, String username) {
        Say.CommentsBean commentsBean = new Say.CommentsBean();
        commentsBean.setId("");
        commentsBean.setComment(comment);
        commentsBean.setUser_id(userId);
        commentsBean.setUsername(username);
        sayList.get(position).getComments().add(commentsBean);
        sayAdapter.notifyItemChanged(position);
    }

    @Override
    public void loadFailure() {
        lRecyclerView.refreshComplete(0);
    }

    @Override
    public void loadMoreFailure() {
        --currentPage;
    }

    private PopupWindow addCommitWindow;
    private EditText editText;
    private Button button;
    private PopupWindow saySetWindow;

    //展示说说设置菜单
    private void showSaySetMenu(ImageView imageView, final String sayId, final int position){
        try{
            View saySetPopupWindow = LayoutInflater.from(context).inflate(R.layout.popup_say_set_choose,rootView,false);
            saySetWindow = new PopupWindow(saySetPopupWindow, ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context)/2-100);

            TextView tv_dislike = saySetPopupWindow.findViewById(R.id.tv_dislike_say);
            TextView tv_hide_user_say = saySetPopupWindow.findViewById(R.id.tv_hide_user_say);
            TextView tv_report = saySetPopupWindow.findViewById(R.id.tv_report);
            TextView tv_follow = saySetPopupWindow.findViewById(R.id.tv_follow);
            TextView tv_unfollow = saySetPopupWindow.findViewById(R.id.tv_unfollow);
            //不喜欢该说说
            tv_dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sayPresenter.disLikeSay(sayId,position+1);
                    saySetWindow.dismiss();
                }
            });
            //隐藏此人动态
            tv_hide_user_say.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saySetWindow.dismiss();
                }
            });
            //举报
            tv_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saySetWindow.dismiss();
                    Intent intent = new Intent(context, FeedBackActivity.class);
                    intent.putExtra("title","举报");
                    intent.putExtra("content","说说id:"+sayId+"\n"+"说说内容:"+sayList.get(position).getContent());
                    startActivity(intent);
                }
            });
            tv_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sayPresenter.follow(sayList.get(position).getUser_id());
                    saySetWindow.dismiss();
                }
            });
            tv_unfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sayPresenter.follow(sayList.get(position).getUser_id());
                    saySetWindow.dismiss();
                }
            });

            saySetWindow.setFocusable(true);
            saySetWindow.setOutsideTouchable(true);
            //对低等级 API 不进行背景设置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rootView.setForeground(getResources().getDrawable(R.color.bg_black_shadow));
            }else {
                ToastUtils.showToast(context,"此设备API较低 可能会出现无法预料的情况。",Toast.LENGTH_LONG);
            }
            saySetWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        rootView.setForeground(getResources().getDrawable(R.color.transparent));
                    }else {
                        ToastUtils.showToast(context,"此设备API较低 可能会出现无法预料的情况。",Toast.LENGTH_LONG);
                    }
                }
            });
            saySetWindow.showAsDropDown(imageView,0,20);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //展示评论框
    private void showCommitView(final int position, final String sayId,final String username) {

        if (addCommitWindow == null || button == null || editText == null) {
            final View popupWindowLayout = LayoutInflater.from(context).inflate(R.layout.popwin_addcommit, rootView, false);
            button = (Button) popupWindowLayout.findViewById(R.id.btn_addcomment_submit);
            editText = (EditText) popupWindowLayout.findViewById(R.id.et_addcomment_content);
            addCommitWindow = new PopupWindow(popupWindowLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addCommitWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            addCommitWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        }
        if (editText != null) {
            CommUtil.showSoftInput(context, editText);
            if (username != null && username.length()>0){
                editText.setText("@" + username + ":");
                editText.setFocusable(true);
                editText.setSelection(username.length() + 2);
            }
        }


        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ButtonUtils.isFastDoubleClick()) {
                        KeyBoardUtils.hideSoftInput(context, activity.getWindow());

                        String comment = editText.getText().toString();

                        if (addCommitWindow.isShowing())
                            addCommitWindow.dismiss();

                        if (TextUtils.isEmpty(comment)) {
                            ToastUtils.showToastShort("请填写评论内容！");
                            return;
                        }

                        sayPresenter.addComment(comment, sayId, position);
                    }
                }
            });
        }
        addCommitWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                editText.setText("");
                KeyBoardUtils.hideSoftInput(context, activity.getWindow());
            }
        });

        addCommitWindow.setFocusable(true);
        //设置点击外部可消失
        addCommitWindow.setOutsideTouchable(true);
        addCommitWindow.setBackgroundDrawable(new BitmapDrawable());

        addCommitWindow.showAtLocation(rootView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST) {
            switch (resultCode) {
                case Constants.DELETE:
                    int position = data.getIntExtra("position", -1);
                    if (position != -1) {
                        deleteItem(position);
                    }
                    break;
                case Constants.PUBLISH:
                    refreshData();
                    break;
                case Constants.CHANGE:
                    break;
            }
        }
    }

    public void refreshData() {
        if (lRecyclerView!=null){
            lRecyclerView.forceToRefresh();
        }
    }

    public void deleteItem(int position) {
        sayList.remove(position);
        lRecyclerViewAdapter.notifyItemRemoved(position);
    }

    public void changetItem(int position, Say say) {
        sayList.remove(position);
        sayList.add(position, say);
        lRecyclerViewAdapter.notifyItemChanged(position);
    }
}