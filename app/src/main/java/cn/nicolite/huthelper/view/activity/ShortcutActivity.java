package cn.nicolite.huthelper.view.activity;

import android.view.View;
import android.widget.Button;


import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.utils.ShortcutUtil;


public class ShortcutActivity extends BaseActivity<IBaseView, BaseActivity> {
    @BindView(R.id.btn_shortcut_course)
    Button btn_short_coures;
    @BindView(R.id.btn_shortcut_human)
    Button btn_shortcut_human;


    @OnClick({R.id.btn_shortcut_course, R.id.btn_shortcut_human})

    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btn_shortcut_course:

                break;
            case R.id.btn_shortcut_human:
                ShortcutUtil.addShortcutSay(getApplicationContext());
                break;
        }
    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_shortcut;
    }

    @Override
    protected void doBusiness() {


    }
}
