package cn.nicolite.huthelper.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.model.bean.EmptyRoom;
import cn.nicolite.huthelper.model.bean.User2;
import cn.nicolite.huthelper.network.APIUtils;
import cn.nicolite.huthelper.utils.DateUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.view.activity.EmptyRoomActivity;
import cn.nicolite.huthelper.view.iview.IEmptyRoomView;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmptyRoomPreseter extends BasePresenter<IEmptyRoomView,EmptyRoomActivity> {

    public EmptyRoomPreseter(IEmptyRoomView view, EmptyRoomActivity activity) {
        super(view, activity);
    }

    /**
     * 获取用户token
     */
    public void getUserToken(String studentKh,String pwd){

        APIUtils
                .INSTANCE
                .getLoginAPI2()
                .getUserToken(studentKh,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User2>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                            if (getView()!=null){
                                getView().showLoading();
                            }
                    }

                    @Override
                    public void onNext(User2 user2) {
                            if (getView()!=null){
                                getView().closeLoading();
                            }
                            if (user2.getToken()=="-1"){
                                getView().showMessage("密码错误");
                                return;
                            }
                            if (user2.getSuccess()){

                                //存储用户token 用登录用户的名称作为文件名
                                String token =  user2.getToken();
                                SharedPreferences.Editor token_editer = getActivity().getSharedPreferences(getLoginUser().toString(),Context.MODE_PRIVATE).edit();
                                token_editer.putString("token",token);
                                token_editer.apply();
                                getView().showMessage("登录成功");
                            }else {
                                if (getView()!=null){
                                    getView().showMessage("登录失败");
                                }

                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            getView().showMessage("未知错误请点击右上角重新登录！");
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showEmptyRoom(String time,String idleTime,String jxlid,String token){

        //取出存储的用户token
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences(getLoginUser(),Context.MODE_PRIVATE);
        APIUtils
                .INSTANCE
                .getEmptyRoomAPI()
                .getEmptyRoom(time,idleTime,jxlid,sharedPreferences.getString("token",null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmptyRoom>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (getView()!=null){
                            getView().showLoading();
                        }
                    }

                    @Override
                    public void onNext(EmptyRoom emptyRoom) {
                            if (getView()!=null){
                                getView().closeLoading();
                            }
                            if (getView()!=null){
                                if (!emptyRoom.getSuccess()){
                                    getView().showMessage("未登录或者不在教学期间内");
                                }else if (emptyRoom.getSuccess() && emptyRoom.getData().size()!=0){
                                    getView().showEmptyRoom(emptyRoom.getData().get(0).getJsList());
                                }else if (emptyRoom.getSuccess() && emptyRoom.getData().size()==0){
                                    List<EmptyRoom.Data.JsList> list = new ArrayList<>();
                                    getView().showEmptyRoom(list);
                                    getView().showMessage("此教学楼暂无空教室");
                                }
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            getView().showMessage("获取数据出错，请重试");
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
