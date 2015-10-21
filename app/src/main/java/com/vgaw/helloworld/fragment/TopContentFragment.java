package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.util.DateTools;

import java.util.ArrayList;

/**
 * Created by caojin on 15-10-12.
 */
public class TopContentFragment extends Fragment {
    public static final String TAG = "TOPCONTENTFRAGMENT";

    private TextView tv_date;
    private TextView tv_date_more;

    private String dateBundle;
    public TopContentFragment(){}

    public static TopContentFragment newInstance(String dateBundle) {

        Bundle args = new Bundle();
        args.putString("dateBundle", dateBundle);
        TopContentFragment fragment = new TopContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateBundle = getArguments() != null ? getArguments().getString("dateBundle") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.top_content_fragment, container, false);
        tv_date = (TextView) layout.findViewById(R.id.date);
        tv_date_more = (TextView) layout.findViewById(R.id.date_more);
        tv_date.setText(DateTools.getDate(dateBundle));
        tv_date_more.setText(DateTools.getDateMore(dateBundle));
        return layout;
    }

    public void updateDate(String date){
        tv_date.setText(DateTools.getDate(date));
        tv_date_more.setText(DateTools.getDateMore(date));
    }
}
