package com.vgaw.helloworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vgaw.helloworld.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caojin on 15-10-12.
 */
public class ViewPagerContentFragment extends Fragment {
    public static final String TAG = "VIEWPAGERCONTENTFRAGMENT";

    public ViewPagerContentFragment(){}

    public ArrayList<String> vir_db;
    public Map<Integer, String> cache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vir_db = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            vir_db.add(String.valueOf(i));
        }

        cache = new HashMap<>(3);
        cache.put(2, vir_db.get(0));
        cache.put(0, "");
        cache.put(1, "");
        //return null if does not have the key
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.viewpager_content_fragment, container, false);
        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPageAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        return layout;
    }

    public class MyPageAdapter extends FragmentPagerAdapter {
        private final int COUNT = 3;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return this.COUNT;
        }


        @Override
        public Fragment getItem(int position) {
            return ContentFragment.newInstance(cache.get(position));
        }


    }
}
