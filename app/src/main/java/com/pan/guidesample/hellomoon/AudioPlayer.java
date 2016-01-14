package com.pan.guidesample.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

import com.pan.androidprogrammingdefinitiveguidesample.R;

/*
 * File Name:AudioPlayer
 * Author:Pan
 * Date:2016/1/14 23:06
 * Description:
 */
public class AudioPlayer {

    private MediaPlayer mPlayer;

    public void pause() {
        mPlayer.pause();
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 播放视频
     *
     * @param context 上下文
     */
    public void play(Context context) {
        //播放视频之前，要释放播放器，避免多次播放创建多个播放器实例
        stop();
        mPlayer = MediaPlayer.create(context, R.raw.one_small_step);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        mPlayer.start();
    }
}
