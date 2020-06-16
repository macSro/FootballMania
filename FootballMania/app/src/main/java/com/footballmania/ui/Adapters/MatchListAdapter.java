package com.footballmania.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Match;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.ui.TeamActivity;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class MatchListAdapter extends ArrayAdapter {

    //ToDo: !!!

    private FootballManiaContract.FootballManiaDbHelper dbHelper;
    private int teamId;
    private ArrayList<Integer> teamHomeId, teamAwayId;
   // private int teamAwayId;
    private ArrayList<Match> matchData;
    Context context;
    private final String ARG_NAME_TEAM_ID = "teamId";

    private class ViewHolder{
        TextView competition;
        TextView score;
        TextView teamHome;
        TextView teamAway;
        TextView date;
        ImageView homeLogo, awayLogo;
        //int teamHomeId, teamAwayId;
    }

    public void setMatchData(ArrayList<Match> matchData) {
        this.matchData.clear();
        this.matchData.addAll(matchData);
        notifyDataSetChanged();
    }

    public ArrayList<Match> getMatchData() {
        return matchData;
    }

    public MatchListAdapter(FootballManiaContract.FootballManiaDbHelper dbHelper, ArrayList<Match> matchData, Context context, int teamId){
        super(context, R.layout.row_match, matchData);
        this.matchData = matchData;
        this.context = context;
        this.teamId = teamId;
        this.dbHelper = dbHelper;
    }

    public MatchListAdapter(FootballManiaContract.FootballManiaDbHelper dbHelper, ArrayList<Match> matchData, Context context, int teamId, ArrayList<Integer> teamHomeId, ArrayList<Integer> teamAwayId){
        super(context, R.layout.row_match, matchData);
        this.matchData = matchData;
        this.context = context;
        this.teamId = teamId;
        this.dbHelper = dbHelper;
        this.teamHomeId=teamHomeId;
        this.teamAwayId=teamAwayId;

    }

    @Nullable
    @Override
    public Match getItem(int position) {
        return matchData.size()>0?matchData.get(position):null;
    }

    @SuppressLint("ResourceType")
    public View getView(final int position, View convertView, final ViewGroup parent){
        final Match match = getItem(position);

        final MatchListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new MatchListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_match, parent, false);

            viewHolder.competition = convertView.findViewById(R.id.row_match_competition);
            viewHolder.score = convertView.findViewById(R.id.row_match_score);
            viewHolder.teamHome = convertView.findViewById(R.id.row_match_team_home);
            viewHolder.teamAway = convertView.findViewById(R.id.row_match_team_away);
            viewHolder.date = convertView.findViewById(R.id.row_match_date);
            viewHolder.homeLogo = convertView.findViewById(R.id.homeLogo);
            viewHolder.awayLogo = convertView.findViewById(R.id.awayLogo);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (MatchListAdapter.ViewHolder) convertView.getTag();
        }

        if(match!=null) {
            viewHolder.competition.setText(dbHelper.getSeasonCompetitionParent(match.getIdSeasonCompetition()).getName());

            viewHolder.score.setText(match.getGoalsHome() + ":" + match.getGoalsAway());

            boolean isTeamHome = false;
            if (match.getIdTeamHome() == teamId) isTeamHome = true;

            viewHolder.score.setTextColor(R.color.colorText);
            if ((match.getGoalsHome() > match.getGoalsAway() && isTeamHome) || (match.getGoalsHome() < match.getGoalsAway() && !isTeamHome)) {
                viewHolder.score.setTextColor(Color.GREEN);
            } else if ((match.getGoalsHome() < match.getGoalsAway() && isTeamHome) || (match.getGoalsHome() > match.getGoalsAway() && !isTeamHome)) {
                viewHolder.score.setTextColor(Color.RED);
            }

            if(teamId==-1) viewHolder.score.setTextColor(convertView.getResources().getColor(R.color.colorText));

            Team teamHome =  dbHelper.getTeam(match.getIdTeamHome());
            Team teamAway =  dbHelper.getTeam(match.getIdTeamAway());

            viewHolder.teamHome.setText(teamHome.getTla());
            viewHolder.teamAway.setText(teamAway.getTla());

            String s =  Utility.getTeamLogoFleName(teamHome.getName());
            int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
            viewHolder.homeLogo.setImageResource(resID);

            s = Utility.getTeamLogoFleName(teamAway.getName());
            resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
            viewHolder.awayLogo.setImageResource(resID);

            if(match.getStatus().equals("POSTPONED") || match.getStatus().equals("LIVE") || match.getStatus().equals("IN PLAY") || match.getStatus().equals("PAUSED") || match.getStatus().equals("SUSPENDED") || match.getStatus().equals("CANCELLED")){
                viewHolder.score.setText("-:-");
                viewHolder.date.setText(match.getStatus());
            }
            else viewHolder.date.setText(Utility.dateLongToString(match.getUtcDate()));


            viewHolder.teamHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(ARG_NAME_TEAM_ID, teamHomeId.get(position));
                    context.startActivity(intent);
                }
            });
            viewHolder.homeLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(ARG_NAME_TEAM_ID, teamHomeId.get(position));
                    context.startActivity(intent);
                }
            });
            viewHolder.teamAway.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("teamId", teamAwayId.get(position));
                    context.startActivity(intent);

                }
            });
            viewHolder.awayLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("teamId", teamAwayId.get(position));
                    context.startActivity(intent);

                }
            });

        }

        return convertView;
    }
}
