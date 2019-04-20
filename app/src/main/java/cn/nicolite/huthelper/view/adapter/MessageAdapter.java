package cn.nicolite.huthelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.model.bean.Message;

public class MessageAdapter extends BaseAdapter {

    ArrayList<Message> message;
    Context context;

    public MessageAdapter(ArrayList<Message> message,Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public int getCount() {
        return message.size();
    }//必须返回消息长度

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.chat_dialog_right_item,parent,false);
        TextView tv_chat_me_message =  convertView.findViewById(R.id.tv_chat_me_message);
        tv_chat_me_message.setText(message.get(position).getMessage());
        return convertView;
    }


}
