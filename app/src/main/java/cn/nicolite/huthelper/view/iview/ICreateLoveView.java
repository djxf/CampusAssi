package cn.nicolite.huthelper.view.iview;

import cn.nicolite.huthelper.base.IBaseView;

public interface ICreateLoveView extends IBaseView {

    void selectImages();

    void uploadLoveInfo(String hidden);

    void uploadProgress(String msg);

    void uploadFailure(String msg);

    void publishSuccess();
}
