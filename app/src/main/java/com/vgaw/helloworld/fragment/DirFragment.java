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
import com.vgaw.helloworld.activity.MainActivity;
import com.vgaw.helloworld.bean.Dir;
import com.vgaw.helloworld.bean.Memo;
import com.vgaw.helloworld.util.DateTools;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by caojin on 15-10-14.
 */
public class DirFragment extends Fragment {
    public static final String TAG = "DIRFRAGMENT";

    private RecyclerView rv_dir;
    private RecyclerView rc_content;

    private ArrayList<Memo> memoList;
    private ArrayList<Dir> dirList;

    public DirFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dir_fragment, container, false);
        rv_dir = (RecyclerView) layout.findViewById(R.id.rv_dir);
        rc_content = (RecyclerView) layout.findViewById(R.id.rv_content);
        rv_dir.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyDirAdapter dirAdapter = new MyDirAdapter();
        MyContentAdapter contentAdapter = new MyContentAdapter();
        rv_dir.setAdapter(dirAdapter);
        rc_content.setAdapter(contentAdapter);

        ((MainActivity)getActivity()).weiTiao();
        dirAdapter.notifyItemInserted(0);
        contentAdapter.notifyItemInserted(0);

        return layout;
    }

    // adapter for dir.
    private class MyDirAdapter extends RecyclerView.Adapter<MyDirAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.dir_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_name.setText(dirList.get(position).getName());
            holder.tv_count.setText(String.valueOf(dirList.get(position).getCount()));
        }

        @Override
        public int getItemCount() {
            if (dirList == null){
                return 0;
            }
            return dirList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name;
            private TextView tv_count;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            }
        }

    }

    /**
     * put the query date into the list.
     *
     * @param memoList
     * @param dirList
     */
    public void setList(ArrayList<Memo> memoList, ArrayList<Dir> dirList) {
        this.memoList = memoList;
        this.dirList = dirList;
    }

    // adapter for content.
    private class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.content_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_content_brief.setText(memoList.get(position).getContent());
            holder.tv_date.setText(DateTools.getDate(memoList.get(position).getCreate_date()));
        }

        @Override
        public int getItemCount() {
            if (memoList == null){
                return 0;
            }
            return memoList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_content_brief;
            private TextView tv_date;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_content_brief = (TextView) itemView.findViewById(R.id.tv_content_brief);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            }
        }

    }

}
