package cn.nicolite.huthelper.view.iview;

import java.util.List;

import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.bean.EmptyRoom;

public interface IEmptyRoomView extends IBaseView {
    void showEmptyRoom(List<EmptyRoom.Data.JsList> list);
}
