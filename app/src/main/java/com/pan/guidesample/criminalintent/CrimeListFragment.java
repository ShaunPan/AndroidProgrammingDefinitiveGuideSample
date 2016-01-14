package com.pan.guidesample.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.pan.androidprogrammingdefinitiveguidesample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/*
 * File Name:CrimeListFragment
 * Author:Better.Z
 * Date:2016/1/13 18:01
 * Description:
 * Copyright:www.YangFanApp.com
 */
public class CrimeListFragment extends ListFragment {

    public static final String TAG = "CrimeListFragment";
    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crime_title);
        mCrimes = CrimeLab.get(getActivity()).getmCrimes();

//        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = (Crime) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
        startActivity(intent);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime crime = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getmTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            SimpleDateFormat format = new SimpleDateFormat("EEEE,MMM dd,yyyy", Locale.CHINA);
            dateTextView.setText(format.format(crime.getmDate()));
            CheckBox solvedCheckbox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckbox);
            solvedCheckbox.setChecked(crime.ismSolved());
            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //更新list view
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
