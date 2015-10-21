package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vgaw.helloworld.R;
import com.vgaw.helloworld.bean.Dir;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/18.
 */
public class ListRecyclerViewFragment extends Fragment {
    public static final String TAG = "LISTRECYCLERVIEWFRAGMENT";

    private ArrayList<Dir> dirList;
    private MyAdapter adapter;

    public ListRecyclerViewFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_recyclerview, container, false);
        RecyclerView rv_list = (RecyclerView) layout.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter();
        rv_list.setAdapter(adapter);
        return layout;
    }

    public void setList(ArrayList<Dir> dirList){
        this.dirList = dirList;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.list_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_name.setText(dirList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return dirList == null ? 0 : dirList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private TextView tv_name;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
