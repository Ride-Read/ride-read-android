package com.rideread.rideread.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.rideread.rideread.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Jackbing on 2017/3/12.
 */

public class ImageFragment extends Fragment{

    private final static String IMAGE_URL="image";
    private PhotoView photoView;
    private String imgUrl;

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgUrl=getArguments().getString(IMAGE_URL);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View mview=inflater.inflate(R.layout.imagefragment,container,false);
        photoView=(PhotoView) mview.findViewById(R.id.photoview);

        Glide.with(this).load(imgUrl).thumbnail(0.5f).fitCenter().into(photoView);
        return mview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



}
