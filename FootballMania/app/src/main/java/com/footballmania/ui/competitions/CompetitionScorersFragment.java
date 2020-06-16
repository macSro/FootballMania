package com.footballmania.ui.competitions;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.database.dbobjects.Scorer;
import com.footballmania.ui.Adapters.ScorersListAdapter;

import java.util.ArrayList;


public class CompetitionScorersFragment extends Fragment {


    public static String FRAGMENT_TAB_TITLE;

    TableLayout mTableLayout;
    ListView mListView;
    ArrayList<Scorer> scorers;
    ScorersListAdapter scorersListAdapter;

    Competition competition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FRAGMENT_TAB_TITLE = getString(R.string.scorers_tab);
        competition = CompetitionActivity.currentCompetition;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(competition.getName().equals("European Championship") ){
            View root =  inflater.inflate(R.layout.competition_no_data, container, false);
            return root;
        }

        View root =  inflater.inflate(R.layout.fragment_competition_scorers, container, false);

        mTableLayout = (TableLayout)  root.findViewById(R.id.table);
        mListView = (ListView) root.findViewById(R.id.scorersListView);

        scorers = FootballManiaContract.getDbHelperInstance(getContext()).getScorersList(competition.getId());

        scorersListAdapter = new ScorersListAdapter(getActivity().getApplicationContext(), scorers);
        mListView.setAdapter(scorersListAdapter);

        return root;
    }
}
