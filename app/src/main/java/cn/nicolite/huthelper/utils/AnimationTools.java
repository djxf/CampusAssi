package cn.nicolite.huthelper.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * 动画工具
 * Created by gaop on 16-9-10.
 */
public class AnimationTools {

    /**
     * 缩放
     * @param v
     */
    public static void scale(View v) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(300);
        v.startAnimation(anim);
    }

    /**
     * 旋转
     * @param view
     */
    public static void rotate(View view){

    }





}
