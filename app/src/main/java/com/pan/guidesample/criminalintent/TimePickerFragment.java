package com.pan.guidesample.criminalintent;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.pan.androidprogrammingdefinitiveguidesample.R;

/*
 * File Name:TimePickerFragment
 * Author:Pan
 * Date:2016/1/14 17:07
 * Description:
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.pan.guidesample.criminalintent.time";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        return new TimePickerDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }

    /*private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,);
    }*/
}
