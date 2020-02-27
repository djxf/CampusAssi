package cn.nicolite.huthelper.network.glide.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import cn.nicolite.huthelper.network.glide.intercepter.ProgressInterceptor;
import okhttp3.OkHttpClient;

public class OkHttpLibraryGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {




    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //添加拦截器到Glide
        OkHttpClient.Builder builder1 = new OkHttpClient.Builder();
        builder1.addInterceptor(new ProgressInterceptor());
        OkHttpClient client = builder1.build();

        glide.register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(client));
    }
}
