package cn.nicolite.huthelper.view.iview;

import java.util.List;

import cn.nicolite.huthelper.base.IBaseView;

/**
 * Created by nicolite on 17-11-15.
 */

public interface ICreateSayView extends IBaseView {
    void selectImages();

    void uploadSayInfo(String imagesInfo);

    void uploadProgress(String msg);

    void uploadFailure(String msg);

    void publishSuccess();
    //展示选择标签视图
    void showSelectLableView(List<String> list);
}
