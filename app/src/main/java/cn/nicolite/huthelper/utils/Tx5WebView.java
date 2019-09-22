package cn.nicolite.huthelper.utils;

import android.content.Context;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class Tx5WebView extends WebView {
    public Tx5WebView(Context context, boolean b) {
        super(context, b);
    }

    private WebViewClient client = new WebViewClient(){

        //防止调起系统浏览器
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return super.shouldOverrideUrlLoading(webView, s);
        }
    };
}
