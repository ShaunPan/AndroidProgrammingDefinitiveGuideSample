package com.pan.guidesample.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.pan.androidprogrammingdefinitiveguidesample.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * File Name:DatePickerFragment
 * Author:Better.Z
 * Date:2016/1/14 10:41
 * Description:
 * Copyright:www.YangFanApp.com
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.pan.guidesample.criminalintent.date";
    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //将日历中的Calendar类型转换成Date类型
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                Log.d("DatePickerFragment", "year:" + year + " monthOfYear:" + monthOfYear + " dayOfMonth" + dayOfMonth);
                //保存数据，避免屏幕旋转时，数据丢失。
                //当DatePicker正显示在屏幕上，这时屏幕发生旋转，
                //将会销毁该fragment实例并重新执行onCreateDialog创建一个实例，进而重新获取参数
                //显然这种方法要比onSaveInstanceState方法要更简洁
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);

    }
}
