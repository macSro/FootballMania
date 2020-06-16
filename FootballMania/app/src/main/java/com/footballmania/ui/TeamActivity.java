package com.footballmania.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.Match;
import com.footballmania.database.dbobjects.Player;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.ui.Adapters.MatchListAdapter;
import com.footballmania.ui.Adapters.PlayerListAdapter;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;

public class TeamActivity extends AppCompatActivity {

    private final String ARG_NAME_TEAM_ID = "teamId";
    private int ARG_TEAM_ID_VALUE;

    private TextView GK, LB, CB1, CB2, RB, LM, CM, RM, LW, ST, RW;

    private ImageView teamLogo;
    private TextView teamName;
    private TextView teamWebsite;
    private TextView teamCoach;
    private LinearLayout formationDropDown;
    private LinearLayout playersDropDown;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private TextView loadTextView;
    private TextView hideTextView;
    private ImageButton favouriteButton;

    private ListView attackersListView;
    private ListView midfieldersListView;
    private ListView defendersListView;
    private ListView goalkeepersListView;

    private PlayerListAdapter playerListAdapter1;
    private PlayerListAdapter playerListAdapter2;
    private PlayerListAdapter playerListAdapter3;
    private PlayerListAdapter playerListAdapter4;

    private ListView listView;
    private MatchListAdapter listAdapter;

    private TextView noMatches;
    private ImageView imageNoMatches;

    private ArrayList<Match> matchList;
    private ArrayList<Integer> homeIds;
    private ArrayList<Integer> awayIds;

    private int itemsCounter=10;

    private boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_team);

        homeIds = new ArrayList<>();
        awayIds = new ArrayList<>();

        noMatches = findViewById(R.id.noMatches);
        imageNoMatches = findViewById(R.id.imageNoMatches);
        favouriteButton = findViewById(R.id.setFavouriteTeamImageButton);

        ARG_TEAM_ID_VALUE = getIntent().getIntExtra(ARG_NAME_TEAM_ID, -1);

        isFavourite = getDbHelperInstance(getApplicationContext()).isFavouriteTeam(ARG_TEAM_ID_VALUE);
        if(isFavourite) favouriteButton.setImageResource(R.drawable.ic_favorite_primary_24dp);
        else favouriteButton.setImageResource(R.drawable.ic_favorite_border_primary_24dp);

        final Team team = getDbHelperInstance(getApplicationContext()).getTeam(ARG_TEAM_ID_VALUE);
        ArrayList<Player> players = getDbHelperInstance(getApplicationContext()).getTeamPlayers(ARG_TEAM_ID_VALUE);
        matchList = new ArrayList<>();
        matchList = getDbHelperInstance(getApplicationContext()).getTeamMatchListPast(ARG_TEAM_ID_VALUE);

        teamLogo = findViewById(R.id.imageTeamLogo);

        String s = Utility.getTeamLogoFleName(team.getName());
        int resID = getApplicationContext().getResources().getIdentifier(s, "drawable", getApplicationContext().getPackageName());
        if(resID != 0) teamLogo.setImageResource(resID);
        else {
            teamLogo.setVisibility(View.GONE);
        }

        teamName = findViewById(R.id.teamNameTextView);

        teamName.setText(team.getName());

        teamWebsite = findViewById(R.id.teamWebsiteTextView);

        if(team.getWebsite() != null) teamWebsite.setText(team.getWebsite());
        else teamWebsite.setVisibility(View.GONE);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    favouriteButton.setImageResource(R.drawable.ic_favorite_border_primary_24dp);
                    getDbHelperInstance(getApplication()).deleteFromFavouriteTeams(team.getId());
                }
                else{
                    favouriteButton.setImageResource(R.drawable.ic_favorite_primary_24dp);
                    getDbHelperInstance(getApplication()).insertFavouriteTeam(team.getId());
                }
            }
        });

        final ConstraintLayout field = findViewById(R.id.field);

        final TextView showFormation = findViewById(R.id.showFormationTextView);
        imageView1 = findViewById(R.id.imageView8);
        formationDropDown = findViewById(R.id.formationDropDown);
        formationDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(field.getVisibility()==View.GONE){
                    field.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.ic_arrow_drop_up_24px);
                    showFormation.setText("Hide formation");
                }
                else {
                    field.setVisibility(View.GONE);
                    imageView1.setImageResource(R.drawable.ic_arrow_drop_down_24px);
                    showFormation.setText("Show formation");
                }
            }
        });

        GK = findViewById(R.id.gkTextView);
        LB = findViewById(R.id.lbTextView);
        CB1 = findViewById(R.id.cb1TextView);
        CB2 = findViewById(R.id.cb2TextView);
        RB = findViewById(R.id.rbTextView);
        LM = findViewById(R.id.lmTextView);
        CM = findViewById(R.id.cmTextView);
        RM = findViewById(R.id.rmTextView);
        LW = findViewById(R.id.lwTextView);
        ST = findViewById(R.id.stTextView);
        RW = findViewById(R.id.rwTextView);
        teamCoach = findViewById(R.id.coachTextView);

        boolean gk=false, lb=false, cb1=false, cb2=false, rb=false, lm=false, cm=false, rm=false, lw=false, st=false, rw=false, c=false;

        ArrayList<Player> attackers = new ArrayList<>();
        ArrayList<Player> midfielders = new ArrayList<>();
        ArrayList<Player> defenders = new ArrayList<>();
        ArrayList<Player> goalkeepers = new ArrayList<>();

        for(Player p : players){
            if (p.getPosition().equals("Goalkeeper")) {
                if(!gk){
                    GK.setText(p.getName());
                    gk=true;
                }
                goalkeepers.add(p);
            }
            else if (p.getPosition().equals("Defender")) {
                if(!lb) {
                    LB.setText(p.getName());
                    lb = true;
                }
                else if(!cb1){
                    CB1.setText(p.getName());
                    cb1=true;
                }
                else if(!cb2){
                    CB2.setText(p.getName());
                    cb2=true;
                }
                else if(!rb){
                    RB.setText(p.getName());
                    rb=true;
                }
                defenders.add(p);
            }
            else if (p.getPosition().equals("Midfielder")) {
                if(!lm) {
                    LM.setText(p.getName());
                    lm = true;
                }
                else if(!cm){
                    CM.setText(p.getName());
                    cm=true;
                }
                else if(!rm){
                    RM.setText(p.getName());
                    rm=true;
                }
                midfielders.add(p);
            }
            else if(p.getPosition().equals("Attacker")){
                if(!lw) {
                    LW.setText(p.getName());
                    lw = true;
                }
                else if(!st){
                    ST.setText(p.getName());
                    st=true;
                }
                else if(!rw){
                    RW.setText(p.getName());
                    rw=true;
                }
                attackers.add(p);
            }
            else if(p.getPosition().equals("Coach") && !c){
                teamCoach.setText("Coach: " + p.getName());
                c=true;
            }
            if(gk && lb && cb1 && cb2 && rb && lm && cm && rm && lw && st && rw && c) break;
        }

        attackersListView = findViewById(R.id.attackersListView);
        midfieldersListView = findViewById(R.id.midfieldersListView);
        defendersListView = findViewById(R.id.defendersListView);
        goalkeepersListView = findViewById(R.id.goalkeepersListView);

        playerListAdapter1 = new PlayerListAdapter(attackers, getApplicationContext());
        playerListAdapter2 = new PlayerListAdapter(midfielders, getApplicationContext());
        playerListAdapter3 = new PlayerListAdapter(defenders, getApplicationContext());
        playerListAdapter4 = new PlayerListAdapter(goalkeepers, getApplicationContext());

        attackersListView.setAdapter(playerListAdapter1);
        midfieldersListView.setAdapter(playerListAdapter2);
        defendersListView.setAdapter(playerListAdapter3);
        goalkeepersListView.setAdapter(playerListAdapter4);

        setListViewHeight(attackers.size(), playerListAdapter1, attackersListView);
        setListViewHeight(midfielders.size(), playerListAdapter2, midfieldersListView);
        setListViewHeight(defenders.size(), playerListAdapter3, defendersListView);
        setListViewHeight(goalkeepers.size(), playerListAdapter4, goalkeepersListView);

        attackersListView.setEnabled(false);
        midfieldersListView.setEnabled(false);
        defendersListView.setEnabled(false);
        goalkeepersListView.setEnabled(false);

        final LinearLayout playersLayout = findViewById(R.id.playersLayout);
        playersLayout.setVisibility(View.GONE);

        final TextView showPlayers = findViewById(R.id.showPlayersTextView);
        imageView4 = findViewById(R.id.imageView11);
        playersDropDown = findViewById(R.id.playersDropDown);
        playersDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playersLayout.getVisibility()==View.GONE){
                    playersLayout.setVisibility(View.VISIBLE);
                    imageView4.setImageResource(R.drawable.ic_arrow_drop_up_24px);
                    showPlayers.setText("Hide all players");
                }
                else {
                    playersLayout.setVisibility(View.GONE);
                    imageView4.setImageResource(R.drawable.ic_arrow_drop_down_24px);
                    showPlayers.setText("Show all players");
                }
            }
        });

        imageView2 = findViewById(R.id.imageView9);
        imageView3 = findViewById(R.id.imageView10);

        imageView2.setVisibility(View.GONE);

        final TextView tv = findViewById(R.id.recentMatchesTextView);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.getText().equals("Upcoming matches")){
                    tv.setText("Live matches");
                    matchList = getDbHelperInstance(getApplicationContext()).getTeamMatchListLive(ARG_TEAM_ID_VALUE);
                    homeIds.clear();
                    awayIds.clear();
                    for(int i=0;i<matchList.size();i++) {
                        homeIds.add(matchList.get(i).getIdTeamHome());
                        awayIds.add(matchList.get(i).getIdTeamAway());
                    }
                    if(matchList.size()<10) setListViewHeight(matchList.size(), listAdapter,listView);
                    if(matchList.size()==0){
                        loadTextView.setVisibility(View.GONE);
                        hideTextView.setVisibility(View.GONE);
                        noMatches.setVisibility(View.VISIBLE);
                        imageNoMatches.setVisibility(View.VISIBLE);
                    }
                    listAdapter.setMatchData(matchList);
                    imageView3.setVisibility(View.VISIBLE);
                    if(matchList.size()>0){
                        loadTextView.setVisibility(View.VISIBLE);
                    }
                }
                else if(tv.getText().equals("Live matches")){
                    tv.setText("Recent matches");
                    matchList = getDbHelperInstance(getApplicationContext()).getTeamMatchListPast(ARG_TEAM_ID_VALUE);
                    homeIds.clear();
                    awayIds.clear();
                    for(int i=0;i<matchList.size();i++) {
                        homeIds.add(matchList.get(i).getIdTeamHome());
                        awayIds.add(matchList.get(i).getIdTeamAway());
                    }
                    if(matchList.size()<10) setListViewHeight(matchList.size(), listAdapter,listView);
                    if(matchList.size()==0){
                        loadTextView.setVisibility(View.GONE);
                        hideTextView.setVisibility(View.GONE);
                    }
                    listAdapter.setMatchData(matchList);
                    imageView2.setVisibility(View.INVISIBLE);
                    if(matchList.size()>0){
                        loadTextView.setVisibility(View.VISIBLE);
                        noMatches.setVisibility(View.GONE);
                        imageNoMatches.setVisibility(View.GONE);
                    }
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.getText().equals("Recent matches")){
                    tv.setText("Live matches");
                    matchList = getDbHelperInstance(getApplicationContext()).getTeamMatchListLive(ARG_TEAM_ID_VALUE);
                    homeIds.clear();
                    awayIds.clear();
                    for(int i=0;i<matchList.size();i++) {
                        homeIds.add(matchList.get(i).getIdTeamHome());
                        awayIds.add(matchList.get(i).getIdTeamAway());
                    }
                    if(matchList.size()<10) setListViewHeight(matchList.size(), listAdapter,listView);
                    if(matchList.size()==0){
                        loadTextView.setVisibility(View.GONE);
                        hideTextView.setVisibility(View.GONE);
                        noMatches.setVisibility(View.VISIBLE);
                        imageNoMatches.setVisibility(View.VISIBLE);
                    }
                    listAdapter.setMatchData(matchList);
                    imageView2.setVisibility(View.VISIBLE);
                    if(matchList.size()>0){
                        loadTextView.setVisibility(View.VISIBLE);
                    }
                }
                else if(tv.getText().equals("Live matches")){
                    tv.setText("Upcoming matches");
                    matchList = getDbHelperInstance(getApplicationContext()).getTeamMatchListUpcoming(ARG_TEAM_ID_VALUE);
                    homeIds.clear();
                    awayIds.clear();
                    for(int i=0;i<matchList.size();i++) {
                        homeIds.add(matchList.get(i).getIdTeamHome());
                        awayIds.add(matchList.get(i).getIdTeamAway());
                    }
                    if(matchList.size()<10) setListViewHeight(matchList.size(),listAdapter,listView);
                    if(matchList.size()==0){
                        loadTextView.setVisibility(View.GONE);
                        hideTextView.setVisibility(View.GONE);

                    }
                    listAdapter.setMatchData(matchList);
                    imageView3.setVisibility(View.INVISIBLE);
                    if(matchList.size()>0){
                        loadTextView.setVisibility(View.VISIBLE);
                        noMatches.setVisibility(View.GONE);
                        imageNoMatches.setVisibility(View.GONE);
                    }
                }
            }
        });

        hideTextView = findViewById(R.id.hideTextView);
        hideTextView.setVisibility(View.GONE);

        loadTextView = findViewById(R.id.loadMoreTextView);
        loadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listAdapter.getMatchData().size()>0) {
                    setListViewHeight(itemsCounter, listAdapter, listView);
                    hideTextView.setVisibility(View.VISIBLE);
                    itemsCounter += 10;
                }
                if(itemsCounter>=listAdapter.getMatchData().size()){
                    loadTextView.setVisibility(View.GONE);
                }
            }
        });

        hideTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListViewHeight(0, listAdapter, listView);
                itemsCounter=10;
                hideTextView.setVisibility(View.GONE);
                loadTextView.setVisibility(View.VISIBLE);
            }
        });

        listView = findViewById(R.id.recentMatchesListView);


        for(int i=0;i<matchList.size();i++) {
            homeIds.add(matchList.get(i).getIdTeamHome());
            awayIds.add(matchList.get(i).getIdTeamAway());
        }

        listAdapter = new MatchListAdapter(getDbHelperInstance(getApplicationContext()), matchList, getApplicationContext(), ARG_TEAM_ID_VALUE, homeIds, awayIds);

        listView.setAdapter(listAdapter);

        attackersListView.setEnabled(false);
        midfieldersListView.setEnabled(false);
        defendersListView.setEnabled(false);
        goalkeepersListView.setEnabled(false);

        if(matchList.isEmpty()) {
            noMatches.setVisibility(View.VISIBLE);
            imageNoMatches.setVisibility(View.VISIBLE);
            loadTextView.setVisibility(View.GONE);
        }
        else {
            noMatches.setVisibility(View.GONE);
            imageNoMatches.setVisibility(View.GONE);
            loadTextView.setVisibility(View.VISIBLE);
        }

        listView.setEnabled(false);
    }

    private void setListViewHeight(int itemCount, ArrayAdapter listAdapter, ListView listView){
        if(itemCount>listAdapter.getCount()) itemCount=listAdapter.getCount();
        int totalHeight =0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for(int i=0; i<itemCount; i++){
            View listItem = listAdapter.getView(i,null, listView);
            listItem.measure(desiredWidth,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (itemCount-1));
        if(itemCount==0) params.height = 0;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
