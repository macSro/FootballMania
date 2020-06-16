package com.footballmania.ui.competitions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.database.dbobjects.TeamStanding;
import com.footballmania.ui.Adapters.TeamStandingsAdapter;
import com.footballmania.ui.TeamActivity;

import java.util.ArrayList;

public class InternationalStandingsFragment extends Fragment {


    public static String FRAGMENT_TAB_TITLE;
    private final String ARG_NAME_TEAM_ID = "teamId";

    TableLayout mTableLayout;
    ListView mListView;
    ImageView header, header2;
    TeamStandingsAdapter teamStandingsAdapter;
    ArrayList<Team> teams;
    ArrayList<TeamStanding> standings;

    Competition competition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FRAGMENT_TAB_TITLE = getString(R.string.standings_tab);
        competition = CompetitionActivity.currentCompetition;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root =  inflater.inflate(R.layout.fragment_international_standing, container, false);
        header = (ImageView)  root.findViewById(R.id.tableHeader);
        header = (ImageView)  root.findViewById(R.id.tableHeader2);

        mListView = (ListView) root.findViewById(R.id.teamsListView);

        Pair<ArrayList<Team>, ArrayList<TeamStanding>> pair = FootballManiaContract.getDbHelperInstance(getContext()).getTeamAndTeamStandingLists(competition.getId());
        teams = pair.first;
        standings = pair.second;

        teamStandingsAdapter = new TeamStandingsAdapter(getActivity().getApplicationContext(), standings);
        mListView.setAdapter(teamStandingsAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra(ARG_NAME_TEAM_ID, teams.get(i).getId());
                System.out.println(teams.get(i).getId());
                startActivity(intent);
            }
        });

        return root;
    }

}
