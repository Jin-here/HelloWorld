package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vgaw.helloworld.R;

/**
 * Created by Administrator on 2015/10/18.
 */
public class TopAddToDirFragment extends Fragment {
    public static final String TAG = "TOPADDTODIRFRAGMENT";

    private EditText et_search;

    public TopAddToDirFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.top_add_to_dir_fragment, container, false);
        et_search = (EditText) layout.findViewById(R.id.et_search);
        return layout;
    }

    /**
     * return the current search keyword for searching dirs.
     * @return
     */
    public String getKeyWord(){
        return this.et_search.getText().toString();
    }
}
