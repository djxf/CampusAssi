package cn.nicolite.huthelper.presenter;

import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import android.graphics.drawable.Icon;

import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;



import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.base.BasePresenter;
import cn.nicolite.huthelper.utils.MyReceiver;
import cn.nicolite.huthelper.view.activity.MainActivity;
import cn.nicolite.huthelper.view.activity.SayActivity;



public class ShortCutPresenter extends BasePresenter {
    public ShortCutPresenter(Object view, Object activity) {
        super(view, activity);
    }

    /**
     * android 8.0 及以上
     * @param context
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addShortcut(Context context){

        ShortcutManager shortcutManager =  (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);

        if (shortcutManager.isRequestPinShortcutSupported()){
            Intent shortcutIntent = new Intent(context,SayActivity.class);
            shortcutIntent.setAction(Intent.ACTION_VIEW);//action必须设置 否则报错

            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context,"sayShort")
                    .setIcon(Icon.createWithResource(context,R.drawable.xiaoyuanshuoshuo))
                    .setShortLabel("校园段子")
                    .setIntent(shortcutIntent)
                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, MyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            shortcutManager.requestPinShortcut(shortcutInfo, shortcutCallbackIntent.getIntentSender());

        }
    }

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static void addShortcutBelowAndroidN(Context context) {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"校园段子");
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, R.drawable.xiaoyuanshuoshuo);
        shortcutintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(context,SayActivity.class));
        context.sendBroadcast(shortcutintent);
    }

}
