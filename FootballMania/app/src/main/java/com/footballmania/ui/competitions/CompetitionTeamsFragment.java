package com.footballmania.ui.competitions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.database.dbobjects.TeamStanding;
import com.footballmania.ui.Adapters.TeamListAdapter;
import com.footballmania.ui.TeamActivity;

import java.util.ArrayList;


public class CompetitionTeamsFragment extends Fragment {

    public static String FRAGMENT_TAB_TITLE;

    private final String ARG_NAME_TEAM_ID = "teamId";

    TableLayout mTableLayout;
    ListView mListView;
    ArrayList<Team> teams;
    TeamListAdapter teamListAdapter;

    Competition competition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FRAGMENT_TAB_TITLE = getString(R.string.teams_tab);
        competition = CompetitionActivity.currentCompetition;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(competition.getName().equals("European Championship") ){
            View root =  inflater.inflate(R.layout.competition_no_data, container, false);
            return root;
        }

        View root =  inflater.inflate(R.layout.fragment_competition_teams, container, false);

        mTableLayout = (TableLayout)  root.findViewById(R.id.table);
        mListView = (ListView) root.findViewById(R.id.teamListView);

        Pair<ArrayList<Team>, ArrayList<TeamStanding>> pair = FootballManiaContract.getDbHelperInstance(getContext()).getTeamAndTeamStandingLists(competition.getId());
        teams = pair.first;

        if(competition.getName().equals("UEFA Champions League")){
            for(int i=0;i<32;i++) teams.remove(0);
        }

        teamListAdapter = new TeamListAdapter(getActivity().getApplicationContext(), teams);
        mListView.setAdapter(teamListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra(ARG_NAME_TEAM_ID, teamListAdapter.getItem(i).getId());
                System.out.println(teamListAdapter.getItem(i).getId());
                startActivity(intent);
            }
        });

        return root;
    }
}
