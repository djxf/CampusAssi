package cn.nicolite.huthelper.utils;

import android.app.AlertDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.nicolite.huthelper.R;


/**
 * cretae by djxf 21点44分
 */
public class MyDialog {

    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private TextView tv_title1;
    private EditText et_id;
    private EditText ed_pass;
    private Button btOk;


    public MyDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.my_dialog,null);
        tv_title1 = view.findViewById(R.id.md_title);
        et_id = view.findViewById(R.id.yd_et_id);
        ed_pass = view.findViewById(R.id.yd_et_pass);
        btOk = view.findViewById(R.id.md_bt_ok);
        builder = new  AlertDialog.Builder(context)
                .setView(view);
    }



    public MyDialog setTitle(String text) {
        tv_title1.setText(text);
        return this;
    }

   public String getXh(){
        return et_id.getText().toString();
   }

   public String getPassword(){
        return ed_pass.getText().toString();
   }

    public MyDialog setXH(String xh){
        et_id.setText(xh);
        return this;
    }
    public MyDialog setPass(String pass){
        ed_pass.setText(pass);
        return this;
    }




    public MyDialog setPositiveButton(final String text, View.OnClickListener onClickListener) {

        btOk.setText(text);
        if (onClickListener != null) {
            btOk.setOnClickListener(onClickListener);
        } else {
            btOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        return this;
    }



    public MyDialog setCancelable(boolean flag){
        if (dialog == null){
            dialog = builder.create();
        }
        dialog.setCancelable(flag);
        return this;
    }

    public MyDialog setCanceledOnTouchOutside(boolean flag){
        if (dialog == null){
            dialog = builder.create();
        }
        dialog.setCanceledOnTouchOutside(flag);
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
