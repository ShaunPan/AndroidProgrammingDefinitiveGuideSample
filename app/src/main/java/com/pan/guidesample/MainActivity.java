package com.pan.guidesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pan.androidprogrammingdefinitiveguidesample.R;
import com.pan.guidesample.geoquiz.QuizActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 测验
     *
     * @param view 点击的控件
     */
    public void geoQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    /**
     * 陋习记录app
     *
     * @param view 点击的控件
     */
    public void criminalIntent(View view) {

    }

    /**
     * 媒体播放app
     *
     * @param view 点击的控件
     */
    public void helloMoon(View view) {

    }

    /**
     * 书呆子启动器
     *
     * @param view 点击的控件
     */
    public void nerdLauncher(View view) {

    }

    /**
     * 遥控
     *
     * @param view 点击的控件
     */
    public void remoteControl(View view) {

    }

    /**
     * 图片墙
     *
     * @param view 点击的控件
     */
    public void photoGallery(View view) {

    }

    /**
     * 拖动和绘制
     *
     * @param view 点击的控件
     */
    public void dragAndDraw(View view) {

    }

    /**
     * 运行跟踪器
     *
     * @param view 点击的控件
     */
    public void runTracker(View view) {

    }


}
