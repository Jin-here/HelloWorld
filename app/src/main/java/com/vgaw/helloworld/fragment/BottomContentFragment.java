package com.vgaw.helloworld.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.activity.MainActivity;
import com.vgaw.helloworld.ui.MyLinearLayoutManager;

import java.util.ArrayList;

/**
 * Created by caojin on 15-10-12.
 */
// TODO: 2015/10/17 bug need to fix
public class BottomContentFragment extends Fragment {
    public static final String TAG = "BOTTOMCONTENTFRAGMENT";

    private GestureDetector gestureDetector;

    // list that contains zero or one or more itemlines.
    private ArrayList<ItemLine> lineList;
    private int currentIndex = 1;
    private int currentPosition;
    private MyAdapter adapter;

    public BottomContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialLineList();
        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bottom_content_fragment, container, false);
        layout.setLongClickable(true);

        RecyclerView rv_action = (RecyclerView) layout.findViewById(R.id.rv_action);
        rv_action.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        rv_action.setLayoutManager(new MyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_action.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildPosition(view) != 0){
                    outRect.left = getResources().getDimensionPixelSize(R.dimen.content_padding);
                }
            }
        });
        adapter = new MyAdapter();
        adapter.setOnTouchEventListener(new OnTouchEventListener() {
            @Override
            public void onTouch(View v, MotionEvent event, int position) {
                currentPosition = position;
            }
        });
        rv_action.setAdapter(adapter);
        return layout;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int verticalMinDistance = 20;
            int minVelocity = 0;

            float e1x = e1.getX();
            float e1y = e1.getY();
            float e2x = e2.getX();
            float e2y = e2.getY();

            if (Math.abs(e2y - e1y) < (e1x - e2x) && e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                ((MainActivity) getActivity()).changeBT();
            } else if (Math.abs(e2y - e1y) < (e2x - e1x) && e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                ((MainActivity) getActivity()).changeBT();
            } else if (Math.abs(e2x - e1x) < (e1y - e2y) && e1.getY() - e2.getY() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                currentIndex++;
                currentIndex = (currentIndex == lineList.size()) ? lineList.size()-1 : currentIndex;
                adapter.notifyDataSetChanged();
            } else if (Math.abs(e2x - e1x) < (e2y - e1y) && e2.getY() - e1.getY() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                currentIndex--;
                currentIndex = (currentIndex < 0) ? 0 : currentIndex;
                adapter.notifyDataSetChanged();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            actionMapping(currentIndex, currentPosition);
            return super.onSingleTapConfirmed(e);
        }
    }

    // a itemline that contains zero or one or more items.
    private class ItemLine {
        private ArrayList<Integer> itemLine;

        public ItemLine() {
            itemLine = new ArrayList<>();
        }

        public void add(int item) {
            itemLine.add(item);
        }

        public int size(){
            return itemLine == null ? 0 : itemLine.size();
        }

        public int get(int index){
            return itemLine.get(index);
        }
    }

    // put the pic src to the list.
    private void initialLineList() {
        lineList = new ArrayList<>();

        ItemLine itemLine0 = new ItemLine();
        itemLine0.add(R.drawable.delete);
        itemLine0.add(R.drawable.dir);
        itemLine0.add(R.drawable.search);
        lineList.add(itemLine0);

        ItemLine itemLine1 = new ItemLine();
        itemLine1.add(R.drawable.undo);
        itemLine1.add(R.drawable.redo);
        itemLine1.add(R.drawable.select);
        lineList.add(itemLine1);

        ItemLine itemLine2 = new ItemLine();
        itemLine2.add(R.drawable.camera);
        itemLine2.add(R.drawable.picture);
        lineList.add(itemLine2);
    }

    // map the action to the specific method.
    private void actionMapping(int lineListIndex, int itemLineIndex){
        switch (lineListIndex){
            case 0:
                switch (itemLineIndex){
                    case 0:
                        // delete.
                        break;
                    case 1:
                        // dir.
                        ((MainActivity)getActivity()).addToDir();
                        break;
                    case 2:
                        // search.
                        break;
                }
                break;
            case 1:
                switch (itemLineIndex){
                    case 0:
                        // undo.
                        break;
                    case 1:
                        // redo.
                        break;
                    case 2:
                        // select.
                        break;
                }
                break;
            case 2:
                switch (itemLineIndex){
                    case 0:
                        // camera.
                        break;
                    case 1:
                        // picture.
                        break;
                }
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private OnTouchEventListener listener;

        public void setOnTouchEventListener(OnTouchEventListener listener){
            this.listener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.action_pic_item, parent,
                    false), listener);
            return holder;
        }

        @Override
        public int getItemCount() {
            return lineList.get(currentIndex).size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.iv_action.setImageResource(lineList.get(currentIndex).get(position));
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{
            private ImageView iv_action;

            private OnTouchEventListener listener;

            public MyViewHolder(View itemView, OnTouchEventListener listener) {
                super(itemView);
                iv_action = (ImageView) itemView.findViewById(R.id.iv_action);

                this.listener = listener;
                itemView.setOnTouchListener(this);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listener.onTouch(v, event, getPosition());
                return false;
            }
        }

    }

    private interface OnTouchEventListener{
        void onTouch(View v, MotionEvent event, int position);
    }
}
