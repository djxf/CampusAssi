package cn.nicolite.huthelper.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.lang.ref.WeakReference;
import java.util.List;


import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.model.Constants;
import cn.nicolite.huthelper.network.glide.impl.ProgressListener;
import cn.nicolite.huthelper.network.glide.impl.ProgressListenerImpl;
import cn.nicolite.huthelper.network.glide.intercepter.ProgressInterceptor;
import cn.nicolite.huthelper.utils.ListUtils;
import cn.nicolite.huthelper.utils.LogUtils;
import cn.nicolite.huthelper.utils.ToastUtils;
import cn.nicolite.huthelper.view.activity.ShowImageActivity;
import cn.nicolite.huthelper.view.customView.HorizontalProgressBarWithNumber;
import cn.nicolite.huthelper.view.customView.RoundnessProgressBar;


/**
 * Created by nicolite on 17-8-22.
 */

public class ShowImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> images;
    private HorizontalProgressBarWithNumber horizontalProgressBarWithNumber;
    private RoundnessProgressBar roundnessProgressBar;

    public ShowImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return ListUtils.isEmpty(images) ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

       final View view = LayoutInflater.from(context).inflate(R.layout.show_image,null);

        final PhotoView imageView = view.findViewById(R.id.imageView_img);
        final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
        roundnessProgressBar = view.findViewById(R.id.view_progress);
        String url = images.get(position);
        if (!url.startsWith("http") && !url.startsWith("https")) {
            url = Constants.PICTURE_URL + url;
        }

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loding)
                .error(R.drawable.img_error)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (context!=null){
                            Glide
                                    .with(context)
                                    .load(R.drawable.loding)
                                    .asGif()
                                    .into(imageView);
                            attacher.update();
                        }
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (context != null){
                            Glide
                                    .with(context)
                                    .load("")
                                    .placeholder(resource.getCurrent())
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .centerCrop()
                                    .into(imageView);
                            attacher.update();
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setImageDrawable(errorDrawable);
                        attacher.update();
                    }
                });
        container.addView(view);
        return view;
    }
}
