package com.pan.guidesample.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.pan.androidprogrammingdefinitiveguidesample.R;

/*
 * File Name:SingleFragmentActivity
 * Author:Better.Z
 * Date:2016/1/13 18:29
 * Description:Fragment容器抽象类，填充不同的Fragment
 * Copyright:www.YangFanApp.com
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    /**
     * 每个子类必须实现的方法，返回填充的fragment
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
