package com.footballmania.ui.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.TeamStanding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TeamStandingsAdapter extends BaseAdapter {

    Context context;
    ArrayList<TeamStanding> teams;
    LayoutInflater layoutInflater = null;

    public TeamStandingsAdapter(Context context, ArrayList<TeamStanding> teams){
        this.context = context;
        this.teams = teams;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Object getItem(int position) {
        return teams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.test, null);

        TextView teamPosition = (TextView) view.findViewById(R.id.position);
        TextView team = (TextView) view.findViewById(R.id.team);
        TextView points = (TextView) view.findViewById(R.id.points);
        TextView matches = (TextView) view.findViewById(R.id.matches);
        TextView wins = (TextView) view.findViewById(R.id.wins);
        TextView draws = (TextView) view.findViewById(R.id.draws);
        TextView loses = (TextView) view.findViewById(R.id.loses);
        TextView goals = (TextView) view.findViewById(R.id.goals);
        TableRow row = view.findViewById(R.id.competitionRow);

        teamPosition.setText(String.valueOf(position + 1));
        team.setText(teams.get(position).getTeam());
        points.setText(String.valueOf(teams.get(position).getPoints()));
        matches.setText(String.valueOf(teams.get(position).getPlayedGames()));
        wins.setText(String.valueOf(teams.get(position).getWon()));
        draws.setText(String.valueOf(teams.get(position).getDraw()));
        loses.setText(String.valueOf(teams.get(position).getLost()));
        goals.setText(String.valueOf(teams.get(position).getGoalsFor()) + ":" + String.valueOf(teams.get(position).getGoalsAgainst()));
        if(teams.get(position).getPosition()%2==0) row.setBackgroundColor(view.getResources().getColor(R.color.colorItemLightGrey));

        return view;
    }
}