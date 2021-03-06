package com.pan.guidesample.criminalintent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/*
 * File Name:ImageFragment
 * Author:Pan
 * Date:2016/2/16 15:15
 * Description:
 */
public class ImageFragment extends DialogFragment {

    public static final String EXTRA_IMAGE_PATH = "com.pan.guidesample.criminalintent.image_path";
    private ImageView mImageView;

    public static ImageFragment newInstance(String imagePath) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_IMAGE_PATH, imagePath);

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        imageFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());
        String imagePath = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable drawable = PictureUtils.getScaledDrawable(getActivity(), imagePath);
        mImageView.setImageDrawable(drawable);
        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}
