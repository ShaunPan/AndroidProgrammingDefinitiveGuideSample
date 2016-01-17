package com.pan.guidesample.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/*
 * File Name:CrimeLab
 * Author:Pan
 * Date:2016/1/13 17:36
 * Description:
 */
public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
//        mCrimes = new ArrayList<>();
        mSerializer = new CriminalIntentJSONSerializer(appContext, FILENAME);

        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            e.printStackTrace();
            Log.e(TAG,"Error loading crimes:",e);
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    /**
     * 保存数据
     *
     * @return 返回保存状态
     */
    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "Crimes saved to file.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
