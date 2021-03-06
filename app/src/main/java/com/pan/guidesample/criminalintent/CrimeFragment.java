package com.pan.guidesample.criminalintent;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    public static final String EXTRA_CRIME_ID = "com.pan.guidesample.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String REQUEST_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    public static final String TAG = "CrimeFragment";
    private static final String DIALOG_IMAGE = "image";

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
        //开启选项菜单处理
        setHasOptionsMenu(true);
        mCrime = new Crime();
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @TargetApi(11)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        //启用应用图标的导航功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                ActionBar actionBar = getActivity().getActionBar();
                if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
            }

        }

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


        mPhotoButton = (ImageButton) view.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });

        //判断设备是否具备摄像头
        PackageManager packageManager = getActivity().getPackageManager();
        boolean hasACamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||//后置摄像头
                packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||//前置摄像头
                Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD ||//系统版本
                Camera.getNumberOfCameras() > 0;//摄像头数量

        if (!hasACamera) {
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView) view.findViewById(R.id.crime_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = mCrime.getmPhoto();
                if (photo == null) {
                    return;
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                //获取图片路径
                String path = getActivity().getFileStreamPath(photo.getFileName()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
            }
        });
        return view;
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //导航至父Activity
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    /**
     * 展示照片
     */
    private void showPhoto() {
        Photo photo = mCrime.getmPhoto();
        BitmapDrawable bitmapDrawable = null;
        if (photo != null) {
            String path = getActivity().getFileStreamPath(photo.getFileName()).getAbsolutePath();
            bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(), path);
        }
        mPhotoView.setImageDrawable(bitmapDrawable);
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
            //存储日期
            mCrime.setmDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            String fileName = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (fileName != null) {
                Photo photo = new Photo(fileName);
                //存储图片
                mCrime.setmPhoto(photo);
                showPhoto();
//                Log.i(TAG, "Crime:" + mCrime.getmTitle() + "has a photo");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }
}
