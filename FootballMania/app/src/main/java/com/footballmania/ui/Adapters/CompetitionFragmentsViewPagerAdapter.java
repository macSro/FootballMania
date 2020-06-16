package com.footballmania.ui.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.footballmania.ui.competitions.CompetitionScorersFragment;
import com.footballmania.ui.competitions.CompetitionStandingsFragment;
import com.footballmania.ui.competitions.CompetitionTeamsFragment;
import com.footballmania.ui.competitions.InternationalStandingsFragment;

public class CompetitionFragmentsViewPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    private String tabTitles[];

    CompetitionTeamsFragment competitionTeamsFragment;
    CompetitionStandingsFragment competitionStandingsFragment;
    InternationalStandingsFragment internationalStandingsFragment;
    CompetitionScorersFragment competitionScorersFragment;

    public CompetitionFragmentsViewPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        competitionTeamsFragment = new CompetitionTeamsFragment();
        competitionStandingsFragment = new CompetitionStandingsFragment();
        competitionScorersFragment = new CompetitionScorersFragment();
        tabTitles = new String[]{competitionTeamsFragment.FRAGMENT_TAB_TITLE, competitionStandingsFragment.FRAGMENT_TAB_TITLE, competitionScorersFragment.FRAGMENT_TAB_TITLE};
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return competitionTeamsFragment;
            case 1:
                return competitionStandingsFragment;
            case 2:
                return competitionScorersFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
