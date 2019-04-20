package cn.nicolite.huthelper.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.model.bean.EmptyRoom;
import cn.nicolite.huthelper.utils.LogUtils;


public class EmptyClassAdater extends RecyclerView.Adapter<EmptyClassAdater.EmpytyViewHolder> {

    private List<EmptyRoom.Data.JsList> list;
    private Context context;
    public EmptyClassAdater(Context context,List<EmptyRoom.Data.JsList> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public EmpytyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emptyclass,parent,false);
        return new EmpytyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpytyViewHolder holder,int position) {
            holder.tv_campus.setText(list.get(position).getXqmc());
            holder.tv_name.setText(list.get(position).getJsmc());
            holder.tv_number.setText(list.get(position).getZws()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

      class EmpytyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_campus)
        TextView tv_campus;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_number)
        TextView tv_number;

        public EmpytyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
