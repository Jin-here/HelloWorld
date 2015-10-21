package com.vgaw.helloworld.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/10/17.
 */
public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                // father use, child use.
                return false;
            case MotionEvent.ACTION_MOVE:
                // father use, child not.
                return true;
            case MotionEvent.ACTION_UP:
                // father use, child use.
                return false;
        }
        //return super.onInterceptTouchEvent(e);
        return true;
    }
}
