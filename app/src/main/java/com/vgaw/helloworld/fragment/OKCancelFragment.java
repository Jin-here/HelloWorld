package com.vgaw.helloworld.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.activity.MainActivity;

/**
 * Created by Administrator on 2015/10/18.
 */
public class OKCancelFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "OKCANCELFRAGMENT";

    public OKCancelFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.ok_cancel_fragment, container, false);
        ImageView iv_ok = (ImageView) layout.findViewById(R.id.iv_ok);
        ImageView iv_cancel = (ImageView) layout.findViewById(R.id.iv_cancel);
        iv_ok.setOnClickListener(this);
        iv_cancel.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ok:
                ((MainActivity)getActivity()).addToDirOk();
                break;
            case R.id.iv_cancel:
                ((MainActivity)getActivity()).addToDirCancel();
                break;
        }
    }
}
