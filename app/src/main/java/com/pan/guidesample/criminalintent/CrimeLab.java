package com.pan.guidesample.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/*
 * File Name:CrimeLab
 * Author:Better.Z
 * Date:2016/1/13 17:36
 * Description:
 * Copyright:www.YangFanApp.com
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setmTitle("Crime #" + i);
            c.setmSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getmCrimes() {
        return mCrimes;
    }

    /**
     * 根据id获取对应的Crime记录
     *
     * @param id 记录的id
     * @return 返回记录信息对象
     */
    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getmId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
