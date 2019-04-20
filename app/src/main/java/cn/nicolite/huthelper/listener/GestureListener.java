package cn.nicolite.huthelper.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 手势监听
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private int distance = 100;     //左右滑动的最大距离
    private int velocity = 100;     //左右滑动的最大速度
    private GestureDetector gestureDetector;

    public GestureListener(Context context) {

         gestureDetector = new GestureDetector(context,this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // 向左滑
        if (e1.getX() - e2.getX() > distance && Math.abs(velocityX) > velocity) {
            leftSlide(e1.getX() - e2.getX());
        }

        // 向右滑
        if (e2.getX() - e1.getX() > distance && Math.abs(velocityX) > velocity) {
            rightSlide(e2.getX() - e1.getX() );
        }

        return false;
    }

    /**
     * 左滑 子类应重写该方法
     * @return
     */
    protected boolean leftSlide(float distance){
        return true;
    }

    /**
     * 右滑 子类应重写该方法
     * @return
     */
    protected boolean rightSlide(float distance){
        return true;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


}
