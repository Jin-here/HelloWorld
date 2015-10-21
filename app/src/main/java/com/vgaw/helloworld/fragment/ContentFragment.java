package com.vgaw.helloworld.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.activity.MainActivity;
import com.vgaw.helloworld.util.TextEditor;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentFragment extends Fragment {
    public static final String TAG = "CONTENTFRAGMENT";

    private GestureDetector gestureDetector;
    private String content = "";

    private EditText et_content;

    public static ContentFragment newInstance(String num) {

        Bundle args = new Bundle();
        args.putString("num", num);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments()!=null? getArguments().getString("num"):"";
        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.content_fragment, container, false);
        et_content = (EditText) layout.findViewById(R.id.content);
        et_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        et_content.setLongClickable(true);
        et_content.setText(content);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        // save the current memo.
        String content = et_content.getText().toString();
        ((MainActivity)getActivity()).saveMemo(content);
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
                ((MainActivity)getActivity()).moveToRightMemo();
            }else if (Math.abs(e2y-e1y)<(e2x-e1x) && e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                ((MainActivity)getActivity()).moveToLeftMemo();
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
