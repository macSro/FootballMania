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

public class CompetitionStandingsFragment extends Fragment {


    public static String FRAGMENT_TAB_TITLE;
    private final String ARG_NAME_TEAM_ID = "teamId";

    TableLayout mTableLayout;
    ListView[] mListView;
    ImageView[] header;
    TeamStandingsAdapter[] teamStandingsAdapter;
    ArrayList<Team> allTeams;
    ArrayList<Team> [] teams;
    ArrayList<TeamStanding> allStandings;
    ArrayList<TeamStanding> [] standings;
    ArrayList<Team> testTeams;
    ArrayList<TeamStanding> testStandings;
    Competition competition;
    int current=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FRAGMENT_TAB_TITLE = getString(R.string.standings_tab);
        competition = CompetitionActivity.currentCompetition;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(competition.getName().equals("European Championship") ){
            View root =  inflater.inflate(R.layout.competition_no_data, container, false);
            return root;
        }
        else if(competition.getName().equals("FIFA World Cup") || competition.getName().equals("UEFA Champions League")) {
            View root =  inflater.inflate(R.layout.fragment_international_standing, container, false);
            header = new ImageView[8];
            header[0] = (ImageView)  root.findViewById(R.id.tableHeader);
            header[1] = (ImageView)  root.findViewById(R.id.tableHeader2);
            header[2] = (ImageView)  root.findViewById(R.id.tableHeader3);
            header[3] = (ImageView)  root.findViewById(R.id.tableHeader4);
            header[4] = (ImageView)  root.findViewById(R.id.tableHeader5);
            header[5] = (ImageView)  root.findViewById(R.id.tableHeader6);
            header[6] = (ImageView)  root.findViewById(R.id.tableHeader7);
            header[7] = (ImageView)  root.findViewById(R.id.tableHeader8);

            mListView = new ListView[8];
            mListView[0] = (ListView) root.findViewById(R.id.internationalListView);
            mListView[1] = (ListView) root.findViewById(R.id.internationalListView2);
            mListView[2] = (ListView) root.findViewById(R.id.internationalListView3);
            mListView[3] = (ListView) root.findViewById(R.id.internationalListView4);
            mListView[4] = (ListView) root.findViewById(R.id.internationalListView5);
            mListView[5] = (ListView) root.findViewById(R.id.internationalListView6);
            mListView[6] = (ListView) root.findViewById(R.id.internationalListView7);
            mListView[7] = (ListView) root.findViewById(R.id.internationalListView8);

            Pair<ArrayList<Team>, ArrayList<TeamStanding>> pair = FootballManiaContract.getDbHelperInstance(getContext()).getTeamAndTeamStandingLists(competition.getId());
            allTeams = pair.first;
            allStandings = pair.second;

            teams = new ArrayList[8];
            standings = new ArrayList[8];
            teamStandingsAdapter = new TeamStandingsAdapter[8];

            for(int i=0; i<8; i++){

                standings[i] = new ArrayList<>();
                teams[i] = new ArrayList<>();

               for(int j=0; j<4; j++){
                   standings[i].add((allStandings.get(i*4+j)));
                   teams[i].add((allTeams.get(i*4+j)));
                }

                teamStandingsAdapter[i] = new TeamStandingsAdapter(getActivity().getApplicationContext(), standings[i]);
                mListView[i].setAdapter(teamStandingsAdapter[i]);
                final int a=i;
                current = i;
                mListView[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), TeamActivity.class);
                        intent.putExtra(ARG_NAME_TEAM_ID, teams[a].get(i).getId());
                        System.out.println(teams[a].get(i).getId());
                        startActivity(intent);
                    }
                });
            }
            return root;
        }
        else{
            View root =  inflater.inflate(R.layout.fragment_competition_standings, container, false);

            mTableLayout = (TableLayout)  root.findViewById(R.id.table);

            mListView = new ListView[1];
            mListView[0] = (ListView) root.findViewById(R.id.teamsListView);

            Pair<ArrayList<Team>, ArrayList<TeamStanding>> pair = FootballManiaContract.getDbHelperInstance(getContext()).getTeamAndTeamStandingLists(competition.getId());
            allTeams = pair.first;
            allStandings = pair.second;

            teamStandingsAdapter = new TeamStandingsAdapter[1];
            teamStandingsAdapter[0] = new TeamStandingsAdapter(getActivity().getApplicationContext(), allStandings);
            mListView[0].setAdapter(teamStandingsAdapter[0]);

            mListView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                    intent.putExtra(ARG_NAME_TEAM_ID, allTeams.get(i).getId());
                    System.out.println(allTeams.get(i).getId());
                    startActivity(intent);
                }
            });

            return root;
        }
    }
}
