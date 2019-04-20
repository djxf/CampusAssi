package cn.nicolite.huthelper.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.model.bean.Configure;
import cn.nicolite.huthelper.model.bean.Electric;
import cn.nicolite.huthelper.model.bean.Vote;
import cn.nicolite.huthelper.network.APIUtils;
import cn.nicolite.huthelper.network.exception.ExceptionEngine;
import cn.nicolite.huthelper.utils.EncryptUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.activity.ElectricActivity;
import cn.nicolite.huthelper.view.iview.IElectricView;
import cn.nicolite.huthelper.view.customView.CommonDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nicolite on 17-10-31.
 */

public class ElectricPresenter extends BasePresenter<IElectricView, ElectricActivity> {

    public static final String YES = "1";
    public static final String NO = "2";

    public String getPart() {
        return part;
    }

    public String  part = "1";


    public void setPart(String part) {
        this.part = part;
    }

    public ElectricPresenter(IElectricView view, ElectricActivity activity) {
        super(view, activity);
    }

    public void showLouHao() {

        if (TextUtils.isEmpty(userId)) {
            if (getView() != null) {
                getView().showMessage("获取当前登录用户失败，请重新登录！");
            }
            return;
        }

        List<Configure> configureList = getConfigureList();
        if (ListUtils.isEmpty(configureList)) {
            if (getView() != null) {
                getView().showMessage("获取用户信息失败！");
            }
            return;
        }

        Configure configure = configureList.get(0);

        if (!TextUtils.isEmpty(configure.getLou()) && !TextUtils.isEmpty(configure.getHao())) {
            if (getView() != null) {
                getView().showLouHao(configure.getLou(), configure.getHao());
            }
        }

    }

    public void showElectricData(String lou, String hao) {

        if (TextUtils.isEmpty(lou) || TextUtils.isEmpty(hao)) {
            if (getView() != null) {
                getView().showMessage("宿舍楼栋和宿舍号不能为空");
            }
            return;
        }

        configure.setLou(lou);
        configure.setHao(hao);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        String date = simpleDateFormat.format(new Date());
        final String env = EncryptUtils.SHA1(lou + hao + configure.getStudentKH() + configure.getAppRememberCode()+part);

        APIUtils.INSTANCE
                .getElectricAPI()
                .getElectric(part,lou, hao, configure.getStudentKH(), configure.getAppRememberCode(), env)
                .compose(getActivity().<Electric>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Electric>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().showLoading();
                    }

                    @Override
                    public void onNext(Electric electric) {
                        if (getView() != null) {
                            getView().closeLoading();
                            if (electric.getCode() == 200) {
                                String yudian = configure.getYudian();
                                String yue = configure.getYue();

                                if (!TextUtils.isEmpty(yudian) && !TextUtils.isEmpty(yue) &&
                                        !yudian.equals("0.00") && !yue.equals("0.00") &&
                                        electric.getAmmeter().equals("0.00") && electric.getBalance().equals("0.00")) {

                                    Electric electric1 = new Electric();
                                    electric1.setAmmeter("0");
                                    electric1.setBalance("0");
                                    getView().showElectric(electric1);
                                   // ToastUtils.showToastLong("该宿舍数据未同步或不存在");
                                } else {
                                    configure.setYudian(electric.getAmmeter());
                                    configure.setYue(electric.getBalance());
                                    configure.update();
                                    getView().showElectric(electric);
                                }
                            } else {
                                getView().showMessage("获取电费数据失败！ " + electric.getCode());
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

    public void showWeather() {
        getView().showWeather(configure.getCity(), configure.getTmp(), configure.getContent());
    }

    public void showVoteSummary() {

        APIUtils.INSTANCE
                .getVoteAPI()
                .getAirConditionerData(configure.getStudentKH(), configure.getAppRememberCode())
                .compose(getActivity().<Vote>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Vote>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Vote vote) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(vote.getMsg()) && vote.getMsg().equals("令牌错误")) {
                                getView().showMessage(vote.getMsg() + "，请重新登录！");
                                return;
                            }
                            if (vote.isCode()) {
                                getView().showVoteSummary(vote.getData().getYes(), vote.getData().getNo(), vote.getOpt());
                            } else {
                                getView().showMessage("获取投票数据失败！　" + vote.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showMessage("获取投票数据失败，请检查网络！");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void vote(String opt) {

        APIUtils.INSTANCE
                .getVoteAPI()
                .setAirConditionerData(configure.getStudentKH(), configure.getAppRememberCode(), opt)
                .compose(getActivity().<Vote>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Vote>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Vote vote) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(vote.getMsg()) && vote.getMsg().equals("令牌错误")) {
                                getView().showMessage(vote.getMsg() + "，请重新登录！");
                                return;
                            }
                            if (vote.isCode()) {
                                getView().showMessage("投票成功！");
                                getView().showVoteSummary(vote.getData().getYes(), vote.getData().getNo(), vote.getOpt());
                            } else {
                                getView().showMessage("投票失败，你已经投过了！");
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showMessage("投票失败! " + ExceptionEngine.handleException(e).getMsg());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void showVoteDialog(final String opt) {
        final CommonDialog commonDialog = new CommonDialog(getActivity());
        commonDialog
                .setMessage("确认提交？")
                .setPositiveButton("提交", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonDialog.dismiss();
                        vote(opt);
                    }
                })
                .setNegativeButton("不投了", null)
                .show();
    }

}
