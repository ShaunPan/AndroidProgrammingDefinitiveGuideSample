package com.pan.guidesample.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

/*
 * File Name:CriminalActivity
 * Author:Pan
 * Date:2016/1/13 12:28
 * Description:
 */
public class CrimeActivity extends SingleFragmentActivity {

//    private FragmentManager supportFragmentManager;

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new CrimeFragment();
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }*/
}
