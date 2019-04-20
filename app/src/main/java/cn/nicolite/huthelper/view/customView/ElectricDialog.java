package cn.nicolite.huthelper.view.customView;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.nicolite.huthelper.R;

/**
 * 电费对话框，先这样，以后拓展
 * Created by nicolite on 17-11-1.
 */

public class ElectricDialog {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;
    private TextView tvBalance;
    private TextView tvAmmeter;
    private TextView tvTime;
    private Button ok;


    public ElectricDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_electric, null, false);
        //余电
        tvAmmeter = (TextView) view.findViewById(R.id.tv_eledialog_yudian);
        //余额
        tvBalance = (TextView) view.findViewById(R.id.tv_eledialog_yue);
        //时间
        tvTime = (TextView)view.findViewById(R.id.tv_eledialo_time);
        ok = (Button) view.findViewById(R.id.btn_eledialog_ok);

        tvAmmeter.setVisibility(View.GONE);
        tvBalance.setVisibility(View.GONE);
        ok.setVisibility(View.GONE);

        builder = new AlertDialog.Builder(context)
                .setView(view);
    }

    public ElectricDialog setData(String electricity, String balance, String time) {
        if (time == null){
            time = "未找到该宿舍的数据 请重试";
        }else {
            time = "数据抓取于："+time;
        }
        tvAmmeter.setVisibility(View.VISIBLE);
        tvBalance.setVisibility(View.VISIBLE);
        tvTime.setVisibility(View.VISIBLE);
        tvAmmeter.setText(String.valueOf(electricity + " 度"));
        tvBalance.setText(String.valueOf(balance + " 元"));
        tvTime.setText(time);
        return this;
    }

    public ElectricDialog setOkButton(String text, View.OnClickListener onClickListener) {
        ok.setVisibility(View.VISIBLE);
        ok.setText(text);
        if (onClickListener != null) {
            ok.setOnClickListener(onClickListener);
        } else {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        return this;
    }

    public void show() {
        if (dialog == null){
            dialog = builder.create();
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
