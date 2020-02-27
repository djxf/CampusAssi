package cn.nicolite.huthelper.network.glide.impl;

public class ProgressListenerImpl implements ProgressListener {
    private int progress;
    @Override
    public void onProgress(int progress) {
        this.progress = progress;
    }
    public int getProgress(){
        return progress;
    }
}
