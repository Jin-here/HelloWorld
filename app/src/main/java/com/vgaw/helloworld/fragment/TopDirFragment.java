package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vgaw.helloworld.R;

/**
 * Created by caojin on 15-10-14.
 */
public class TopDirFragment extends Fragment {
    public static final String TAG = "TOPDIRFRAGMENT";

    public TopDirFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top_dir_fragment, container, false);
    }
}
