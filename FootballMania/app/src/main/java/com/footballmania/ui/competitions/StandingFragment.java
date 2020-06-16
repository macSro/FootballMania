package com.footballmania.ui.competitions;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.footballmania.R;
import com.google.android.material.tabs.TabLayout;

public class StandingFragment extends Fragment {
//
//    public static final String ARG_ID = "id";
//    public static final String ARG_NAME = "name";
//    public static final String ARG_AREA = "area";
//
//    int id;
//    String name;
//    String area;
//
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    private CompetitionFragmentsViewPagerAdapter standingViewPagerAdapter;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = this.getArguments();
//        if(bundle!=null){
//            id = bundle.getInt(ARG_ID);
//            name = bundle.getString(ARG_NAME);
//            area = bundle.getString(ARG_AREA);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View root =  inflater.inflate(R.layout.standing, container, false);
//
//        tabLayout = root.findViewById(R.id.standingTabLayout);
//        viewPager = root.findViewById(R.id.standingViewPager);
//
//        standingViewPagerAdapter = new CompetitionFragmentsViewPagerAdapter(getFragmentManager());
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        viewPager.setAdapter(standingViewPagerAdapter);
//
//        return root;
//    }
//
//    @Override
//    public void onResume() {
//        //TODO: clear article list and update from contract
//        standingViewPagerAdapter.notifyDataSetChanged();
//        super.onResume();
//    }


}