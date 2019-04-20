package cn.nicolite.huthelper.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BaseActivity;
import cn.nicolite.huthelper.base.IBaseView;
import cn.nicolite.huthelper.model.bean.Message;
import cn.nicolite.huthelper.view.adapter.MessageAdapter;

public class ChatActivity extends BaseActivity<IBaseView, BaseActivity> {


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.list_message)
    ListView list_message;
    @BindView(R.id.bt_send)
    Button bt_send;
    @BindView(R.id.et_message)
    EditText editText;
    ArrayList<Message> messagelist;
    MessageAdapter messageAdapter;
    Context context;
    String username;
    Message message;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void doBusiness() {
        messagelist = new ArrayList<>();
        messageAdapter = new MessageAdapter(messagelist,this);
        list_message.setAdapter(messageAdapter);

    }

    @OnClick({R.id.et_message,R.id.bt_send})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.bt_send:
                String message1 = editText.getText().toString();
                messagelist.add(new Message(message1));
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
                editText.requestFocus();
                break;
        }


    }

    @Override
    protected void initBundleData(Bundle bundle) {
        super.initBundleData(bundle);
        username = bundle.getString("username","未找到用户名");
        tv_name.setText(username);
    }
}
