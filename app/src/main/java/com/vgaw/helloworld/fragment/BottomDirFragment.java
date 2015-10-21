package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.activity.MainActivity;

/**
 * Created by caojin on 15-10-14.
 */
public class BottomDirFragment extends Fragment {
    public static final String TAG = "BOTTOMDIRFRAGMENT";

    private GestureDetector gestureDetector;

    public BottomDirFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bottom_dir_fragment, container, false);
        layout.setLongClickable(true);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        return layout;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int verticalMinDistance = 20;
            int minVelocity         = 0;

            float e1x = e1.getX();
            float e1y = e1.getY();
            float e2x = e2.getX();
            float e2y = e2.getY();

            if (Math.abs(e2y-e1y)<(e1x-e2x) && e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                ((MainActivity)getActivity()).changeBT();
            }else if (Math.abs(e2y-e1y)<(e2x-e1x) && e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                ((MainActivity)getActivity()).changeBT();
            }else if (Math.abs(e2x-e1x)<(e1y-e2y) && e1.getY() - e2.getY() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
            }else if (Math.abs(e2x-e1x)<(e2y-e1y) && e2.getY() - e1.getY() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }
}
