package cn.nicolite.huthelper.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import cn.nicolite.huthelper.R;
import cn.nicolite.huthelper.view.activity.SayActivity;
import cn.nicolite.huthelper.view.activity.SyllabusActivity;

/**
 * 创建删除快捷图标
 * 注意添加权限:
 * com.android.launcher.permission.INSTALL_SHORTCUT
 * com.android.launcher.permission.UNINSTALL_SHORTCUT
 * 不兼容低版本，如api-19以下
 * https://blog.csdn.net/rentee/article/details/77005547
 *
 */
public class ShortcutUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addShortcutSay(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            Intent shortIntent = new Intent(context, SayActivity.class);
            shortIntent.setAction("android.intent.action.Main");

            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context, "saysay")
                    .setIcon(Icon.createWithResource(context, R.drawable.xiaoyuanshuoshuo))
                    .setLongLabel("校园段子")
                    .build();
            //创建时的回调
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,new Intent(context,MyReceiver.class),PendingIntent.FLAG_CANCEL_CURRENT);
            shortcutManager.requestPinShortcut(shortcutInfo,pendingIntent.getIntentSender());
        }else {
            Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"校园段子");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(context, R.drawable.xiaoyuanshuoshuo);
            shortcutintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(context,SayActivity.class));
            context.sendBroadcast(shortcutintent);
        }

    }



}


