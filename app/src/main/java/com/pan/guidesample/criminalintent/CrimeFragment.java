package com.pan.guidesample.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.pan.androidprogrammingdefinitiveguidesample.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/*
 * File Name:CrimeFragment
 * Author:Pan
 * Date:2016/1/13 15:00
 * Description:
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckbox;
    public static final String EXTRA_CRIME_ID = "com.pan.guidesample.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String REQUEST_TIME = "time";
    private static final int REQUEST_DATE = 0;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getmDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePickerFragment.show(supportFragmentManager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                timePickerFragment.show(supportFragmentManager, REQUEST_TIME);
            }
        });

        mSolvedCheckbox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.ismSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        return view;
    }

    private void updateTime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        mTimeButton.setText(df.format(new Date()));
    }

    /**
     * 更新DateButton上的日期
     */
    private void updateDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE,MMM dd,yyyy", Locale.CHINA);
        mDateButton.setText(format.format(mCrime.getmDate()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //存储数据
            mCrime.setmDate(date);
            updateDate();
        }
    }
}
