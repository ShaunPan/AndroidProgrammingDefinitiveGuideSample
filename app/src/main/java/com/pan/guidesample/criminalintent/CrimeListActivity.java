package com.pan.guidesample.criminalintent;

import android.support.v4.app.Fragment;

/*
 * File Name:CrimeListActivity
 * Author:Pan
 * Date:2016/1/13 18:33
 * Description:
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
