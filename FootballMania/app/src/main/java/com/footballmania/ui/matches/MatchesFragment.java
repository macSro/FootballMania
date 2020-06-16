package com.footballmania.ui.matches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.footballmania.R;
import com.google.android.material.tabs.TabLayout;

public class MatchesFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_matches, container, false);

        tabLayout = root.findViewById(R.id.matchesTabLayout);
        viewPager = root.findViewById(R.id.matchesViewPager);

        MatchesViewPagerAdapter matchesViewPagerAdapter = new MatchesViewPagerAdapter(getChildFragmentManager());

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(matchesViewPagerAdapter);

        return root;
    }

    private class MatchesViewPagerAdapter extends FragmentPagerAdapter {


        private String[] tabTitles = new String[]{"Finished", "Live", "Fixtures"};
        private Fragment[] fragments = new Fragment[]{new MatchesListFragment("Finished"), new MatchesListFragment("Live"), new MatchesListFragment("Fixtures")};

        public MatchesViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}