package cn.nicolite.huthelper.view.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.db.DaoUtils;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.model.bean.Say;
import cn.nicolite.huthelper.model.bean.SayLikedCache;
import cn.nicolite.huthelper.utils.AnimationTools;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.TextCopyUtil.OnSelectListener;
import cn.nicolite.huthelper.utils.TextCopyUtil.SelectableTextHelper;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.customView.NinePictureLayout;
import cn.nicolite.huthelper.view.customView.NoScrollLinearLayoutManager;
import cn.nicolite.huthelper.view.customView.PictureLayout;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by djxf on 19-03-10
 */

public class SayAdapter extends RecyclerView.Adapter<SayAdapter.SayViewHolder> {

    private String searchtext;//说说来源方式如果是搜索而来对内容中字体进行加色 1 表示正常
    private Context context;
    private List<Say> sayList;
    private OnItemClickListener onItemClickListener;
    private String userId;
    private static boolean isFirstClick = true;
    private SelectableTextHelper mSelectableTextHelper;

    public SayAdapter(Context context, List<Say> sayList,String searchtext) {
        this.context = context;
        this.sayList = sayList;
        userId = DaoUtils.getLoginUser();
        this.searchtext = searchtext;
    }

    @NonNull
    @Override
    public SayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_say_list, parent, false);
        return new SayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SayViewHolder holder,final int position) {
        final Say say = sayList.get(position);
        List<Say.CommentsBean> comments = say.getComments();
        String imageUrl = TextUtils.isEmpty(say.getHead_pic()) ? Constants.PICTURE_URL + say.getHead_pic_thumb() :
                Constants.PICTURE_URL + say.getHead_pic();
        Glide
                .with(context)
                .load(imageUrl)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.say_default_head)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.say_default_head)
                .dontAnimate()
                .into(holder.ivItemSayAvatar);


        holder.tvItemSayauthor.setText(say.getUsername());
        holder.tvItemSayTime.setText(say.getCreated_on());
        holder.tvItemSayXy.setText(say.getDep_name());
        holder.tvSayItemLikenum.setText(say.getLikes());
        if (TextUtils.isEmpty(say.getType())){
            holder.tv_sayType.setVisibility(View.GONE);
        }else {
            holder.tv_sayType.setVisibility(View.VISIBLE);
            holder.tv_sayType.setText("#"+say.getType());
        }



        if (say.getContent().contains(searchtext) && !searchtext.equals("") && searchtext!=null){
            holder.sayContentMore.setVisibility(View.INVISIBLE);
            final String content = say.getContent();
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")),say.getContent().indexOf(searchtext),say.getContent().indexOf(searchtext)+searchtext.length(),Spanned.SPAN_COMPOSING);
            holder.tvItemSaycontent.setText(stringBuilder);
        }else {
            holder.tvItemSaycontent.setText(say.getContent().length()>200?say.getContent().substring(0,199)+"...":say.getContent());
            //是否显示更多
            if (say.getContent().length()>200){
                holder.sayContentMore.setVisibility(View.VISIBLE);
                holder.sayContentMore.setText("更多");
            }else {
                holder.sayContentMore.setVisibility(View.INVISIBLE);
            }
        }

        if (say.getUser_id().equals(userId)) {
            holder.ivItemDeletesay.setVisibility(View.VISIBLE);
        } else {
            holder.ivItemDeletesay.setVisibility(View.GONE);
        }


        say.setLike(SayLikedCache.isHave(say.getId()));
        // 取出bean中当记录状态是否为true，是的话则给img设置focus点赞图片
        if (say.isLike()) {
            holder.ivSayItemLike.setImageResource(R.drawable.ic_like);
        } else {
            holder.ivSayItemLike.setImageResource(R.drawable.ic_unlike);
        }
        holder.ivSayItemLike.setFocusable(false);
        holder.ivSayItemLike.setFocusableInTouchMode(false);

        holder.ivSayItemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (say.isLike()) {
                        ((ImageView) view).setImageResource(R.drawable.ic_unlike);
                        say.setLike(false);
                        say.setLikes(String.valueOf(Integer.parseInt(say.getLikes()) - 1));
                        SayLikedCache.removeLike(say.getId());
                    } else {
                        ((ImageView) view).setImageResource(R.drawable.ic_like);
                        say.setLike(true);
                        say.setLikes(String.valueOf(Integer.parseInt(say.getLikes()) + 1));
                        SayLikedCache.addLike(say.getId());
                    }
                    holder.tvSayItemLikenum.setText(say.getLikes());
                    AnimationTools.scale(view);
                    onItemClickListener.onLikeClick(say.getId());
                }
            }
        });

        //删除说说
        holder.ivItemDeletesay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteClick(say.getId(),holder.getAdapterPosition());
                }
            }
        });

        final List<String> pics = say.getPics();
        final List<String> picsRaw = new ArrayList<>();

        //暂时返回略缩图片
         for (String item : pics) {
            picsRaw.add(item.replace("_thumb", "_thumb"));
         }
        //说说图片容器
        holder.rvItemSayimg.setUrlList(picsRaw);

        if (ListUtils.isEmpty(pics)) {
            holder.rvItemSayimg.setVisibility(View.GONE);
        } else {
            holder.rvItemSayimg.setVisibility(View.VISIBLE);
        }

        if (ListUtils.isEmpty(comments)) {
            holder.ivItemSay.setVisibility(View.GONE);
            holder.rvSayComments.setVisibility(View.GONE);
            holder.tvSayItemCommitnum.setText("0");
        } else {
            holder.ivItemSay.setVisibility(View.VISIBLE);
            holder.rvSayComments.setVisibility(View.VISIBLE);
            holder.tvSayItemCommitnum.setText(String.valueOf(comments.size()));
        }


        holder.ivItemSayAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserClick(say.getUser_id(), say.getUsername());
                }
            }
        });

        holder.tvItemSayauthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserClick(say.getUser_id(), say.getUsername());
                }
            }
        });


        holder.ivSayItemAddcommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onAddCommentClick(position, say.getId(),"");
                }
            }
        });

        holder.image_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onArrowDownClick(holder.image_arrow_down,say.getId(),position);
            }
        });


        holder.rvItemSayimg.setOnClickImageListener(new PictureLayout.OnClickImageListener() {
            @Override
            public void onClickImage(int position, List<String> urlList) {
                final List<String> picsRaw2 = new ArrayList<>();

                //暂时返回略缩图片
                for (String item : urlList) {
                    picsRaw2.add(item.replace("_thumb", ""));
                }
                onItemClickListener.onImageClick(position, picsRaw2);
            }
        });

        if (!ListUtils.isEmpty(comments)) {
            NoScrollLinearLayoutManager layout = new NoScrollLinearLayoutManager(context, OrientationHelper.VERTICAL, false);
            holder.rvSayComments.setLayoutManager(layout);
            List<Say.CommentsBean> commentsBeanListLimit;
            //是否过多评论标记
            boolean isMoreComment = false;
            //过多评论进行截取
            if (say.getComments().size() >= 10){
                commentsBeanListLimit = say.getComments().subList(0,10);
                isMoreComment = true;
            }else {
                commentsBeanListLimit = say.getComments();
            }
            CommentAdapter commentAdapter = new CommentAdapter(context, commentsBeanListLimit, userId, position);
            holder.rvSayComments.setAdapter(commentAdapter);
            commentAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
                @Override
                public void onUserClick(int position, String userId, String username) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onUserClick(userId, username);
                    }
                }

                @Override
                public void onDeleteClick(int sayPosition, int commentPosition, Say.CommentsBean commentsBean) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onCommentDeleteClick(sayPosition, commentsBean.getId(), commentPosition);
                    }
                }

                @Override
                public void onCommentClick(int position, String username) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onAddCommentClick(position, say.getId(),username);
                    }
                }
            });



        }
        holder.sayContentMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isFirstClick){
                    holder.tvItemSaycontent.setText(say.getContent());
                    holder.sayContentMore.setText("收起");
                    isFirstClick=false;
                }else {
                    if (say.getContent().length()>=200){
                        holder.tvItemSaycontent.setText(say.getContent().substring(0,199)+"...");
                        holder.sayContentMore.setText("更多");
                    }else {
                        holder.tvItemSaycontent.setText(say.getContent());
                        holder.sayContentMore.setText("");
                    }
                    isFirstClick=true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListUtils.isEmpty(sayList) ? 0 : sayList.size();
    }

    @Override public int getItemViewType(int position)
    { return position; }


    static class SayViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.say_root_view)
        RelativeLayout sayRootView;
        @BindView(R.id.iv_item_say_avatar)
        ImageView ivItemSayAvatar;
        @BindView(R.id.tv_item_sayauthor)
        TextView tvItemSayauthor;
        @BindView(R.id.tv_item_say_time)
        TextView tvItemSayTime;
        @BindView(R.id.tv_item_saycontent)
        TextView tvItemSaycontent;
        @BindView(R.id.rv_item_sayimg)
        NinePictureLayout rvItemSayimg;
        @BindView(R.id.tv_item_say_xy)
        TextView tvItemSayXy;
        @BindView(R.id.iv_say_item_addcommit)
        ImageView ivSayItemAddcommit;
        @BindView(R.id.tv_say_item_commitnum)
        TextView tvSayItemCommitnum;
        @BindView(R.id.iv_say_item_like)
        ImageView ivSayItemLike;
        @BindView(R.id.tv_say_item_likenum)
        TextView tvSayItemLikenum;
        @BindView(R.id.iv_item_deletesay)
        ImageView ivItemDeletesay;
        @BindView(R.id.ll_sayitem)
        LinearLayout llSayitem;
        @BindView(R.id.iv_item_say)
        View ivItemSay;
        @BindView(R.id.rv_say_comments)
        RecyclerView rvSayComments;
        @BindView(R.id.tv_saycontent_more)
        TextView sayContentMore;
        @BindView(R.id.image_arrow_down)
        ImageView image_arrow_down;
        @BindView(R.id.tv_item_say_type)
        TextView tv_sayType;
        public SayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //内部接口
    public interface OnItemClickListener {

        void onAddCommentClick(int position, String sayId,String username);

        void onUserClick(String userId, String username);

        void onLikeClick(String sayId);

        void onDeleteClick(String sayId, int position);

        void onImageClick(int position, List<String> urlList);

        void onCommentDeleteClick(int sayPosition, String commentId, int commentPosition);

        //把点击的实例对象和说说id传出去
       void onArrowDownClick(ImageView imageView,String sayId,final int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
