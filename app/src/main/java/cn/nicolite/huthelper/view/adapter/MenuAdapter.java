package cn.nicolite.huthelper.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.model.bean.Menu;
import cn.nicolite.huthelper.utils.ButtonUtils;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.view.customView.CustomItemTouchHelper;

/**
 * 首页菜单适配器
 * Created by nicolite on 17-10-24.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements CustomItemTouchHelper.ItemTouchHelperAdapter {
    private List<Menu> menuList;
    private Context context;
    private boolean isEdit;
    private AdapterView.OnItemClickListener onItemClickListener;
    private EditClickLister editClickListener;

    /**
     * 图片索引
     * 0--图书馆 1--课程表   2--考试计划   3--成绩   4--作业   5--二手市场   6--段子区   7--电费查询
     * 8--实验课  9--校历 10--失物  11--宣讲会  12--全部 13--视频 14--新生攻略  15-社团活动
     * 16-地图 17-社团活动 18-全校课表
     *  19-空教室 20-驾校报名
     */
    private final int[] PIC_INDEX = {R.drawable.tushuguan, R.drawable.kechengbiao, R.drawable.kaoshichaxun, R.drawable.chengjichaxun,//0-3
                                         R.drawable.wangshangzuoye, R.drawable.ershoushichang, R.drawable.xiaoyuanshuoshuo, R.drawable.dianfeichaxun,//4-7
                                         R.drawable.shiyankebiao, R.drawable.rili, R.drawable.shiwuzhaoling, R.drawable.xuanjianghui, //8-11
                                         R.drawable.more, R.drawable.shipinzhuanlan, R.drawable.laoxiangxiaoyou,R.drawable.club,//12-15
                                         R.drawable.hut_map,R.drawable.clubactivity,R.drawable.allclass,//16-17-18
                                         R.drawable.classroom,R.drawable.img_car   //19-20
    };

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_menulist, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        Menu menu = menuList.get(position);
        holder.tvTItle.setText(menu.getTitle());
        Glide
                .with(context)
                .load(PIC_INDEX[menu.getImgId()])
                .skipMemoryCache(true)
                .dontAnimate()
                .into(holder.ivPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null, v, holder.getAdapterPosition(), holder.getItemId());
            }
        });

        if (isEdit) {
            holder.ivEdit.setVisibility(View.VISIBLE);
            if (menu.isMain()) {
                holder.ivEdit.setImageResource(R.drawable.ic_edit_delete);
            } else {
                holder.ivEdit.setImageResource(R.drawable.ic_editadd);
            }
            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonUtils.isFastDoubleClick(-1, 800))
                        return;
                    editClickListener.onEditClick(v, position);
                }
            });
        } else {
            holder.ivEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ListUtils.isEmpty(menuList) ? 0 : menuList.size();
    }


    /**
     * 设置编辑/完成模式
     *
     * @param isEdit
     */
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        menuList.remove(position);
        notifyItemRemoved(position);
    }

    public void insertItem(int position, Menu menu) {
        menuList.add(position, menu);
        notifyItemInserted(position);
    }


    /**
     * 拖拽移动时回调
     *
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(menuList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        menuList.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnEditClickListener(EditClickLister listener) {
        editClickListener = listener;
    }

    public interface EditClickLister {
        void onEditClick(View v, int position);
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menu_title)
        TextView tvTItle;
        @BindView(R.id.iv_menu_edit)
        ImageView ivEdit;
        @BindView(R.id.iv_menu_pic)
        ImageView ivPic;
        @BindView(R.id.rootView)
        RelativeLayout rootView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
