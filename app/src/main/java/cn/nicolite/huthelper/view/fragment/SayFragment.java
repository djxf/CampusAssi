package cn.nicolite.huthelper.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseFragment;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Say;
import cn.nicolite.huthelper.presenter.SayPresenter;
import cn.nicolite.huthelper.utils.ButtonUtils;
import cn.nicolite.huthelper.utils.CommUtil;
import cn.nicolite.huthelper.utils.KeyBoardUtils;

import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.SnackbarUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.activity.SayActivity;
import cn.nicolite.huthelper.view.activity.ShowImageActivity;
import cn.nicolite.huthelper.view.activity.UserInfoCardActivity;
import cn.nicolite.huthelper.view.adapter.SayAdapter;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import cn.nicolite.huthelper.view.iview.ISayView;



/**
 * Created by nicolite on 17-11-14.
 */

public class


SayFragment extends BaseFragment implements ISayView {
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
            if (type == MYSAY) {
                searchText = arguments.getString("searchText", "");
            } else if (type == HOTSAY) {
                searchText = arguments.getString("searchText", "");
            } else if (type == MYTALK) {
                searchText = arguments.getString("searchText", "");
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

        sayAdapter = new SayAdapter(context, sayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(sayAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        sayPresenter = new SayPresenter(this, this);
        sayAdapter.setOnItemClickListener(new SayAdapter.OnItemClickListener() {

            @Override
            public void onAddCommentClick(int position, String sayId) {
                showCommitView(position, sayId);
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
                                sayPresenter.deleteSay(position, sayId);
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
                }
            }
        });

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isNoMore) {
                    switch (type) {
                        case SayFragment.ALLSAY:
                            sayPresenter.loadMore(type, ++currentPage);
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
                            sayPresenter.loadMore(type, currentPage);
                            break;
                        case SayFragment.MYSAY:
                            sayPresenter.loadSayListByUserId(searchText, currentPage, true, true);
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
        sayList.remove(position);
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



    //展示评论框
    private void showCommitView(final int position, final String sayId) {

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
        }


        //
        if (editText != null) {
            EditTextWatch myWatch = new EditTextWatch((SayActivity) activity,editText,position,sayList);
            editText.addTextChangedListener(myWatch);
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
        lRecyclerView.forceToRefresh();
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






    //非静态内部类和匿名内部类会隐式持有外部类的引用，容易发生内存泄漏。静态的内部类不会持有外部类的引用
    private static class EditTextWatch implements TextWatcher {

        private final WeakReference<SayActivity> sayActivityWeakReference;
        private final WeakReference<EditText> editTextWeakReference;
        private EditText editText;
        private List<Say> list;
        public EditTextWatch(SayActivity sayActivity,EditText editText,int position,List<Say> list) {
            sayActivityWeakReference = new WeakReference<>(sayActivity);
            editTextWeakReference = new WeakReference<>(editText);
            this.editText = editTextWeakReference.get();
            this.position = position;
            this.list = list;
        }

        private int position;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            SayActivity sayActivity = sayActivityWeakReference.get();

            if (sayActivity != null){
                try {
                    List<String> commentNameList = new ArrayList<>();
                    //CharSequence 必须toString()
                    if (s.toString().equals("@") && s.toString().length() == 1 ) {
                        //提取评论用户名
                        for (Say.CommentsBean i : list.get(position).getComments()) {
                            if (commentNameList.indexOf(i.getUsername()) == -1) {
                                commentNameList.add(i.getUsername());
                            }
                        }
                        editText.removeTextChangedListener(this);
                        String[] items = new String[commentNameList.size()];
                        commentNameList.toArray(items);
                        showConmentNameList(items);
                        commentNameList.clear();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        public   void showConmentNameList(final String[] items) {

            if (items.length==0){
                ToastUtils.showToastLong("当前无可回复用户");
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(sayActivityWeakReference.get());
            builder.setTitle("回复")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editText.setText("@" + items[i] + ":");
                            editText.setFocusable(true);
                            editText.setSelection(items[i].length()+2);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }
}