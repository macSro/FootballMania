package com.footballmania.ui.matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Match;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.ui.Adapters.FavouriteTeamsAdapter;
import com.footballmania.ui.Adapters.MatchListAdapter;
import com.footballmania.ui.TeamActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MatchesListFragment extends Fragment {

    private final String ARG_NAME_TEAM_ID = "teamId";
    private int ARG_TEAM_ID_VALUE = 63;
    private String matchStatus;

    public MatchesListFragment(String s){
        this.matchStatus = s;
    }

    private ListView listView;
    private MatchListAdapter listAdapter;
    private TextView loadTextView;
    private TextView hideTextView;
    private TextView noMatches;
    private ImageView imageNoMatches;

    private ArrayList<Match> matchList;
    private ArrayList<Integer> homeIds;
    private ArrayList<Integer> awayIds;
    private int itemsCounter=10;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.matches_list_fragment, container, false);

        noMatches = root.findViewById(R.id.noMatches);
        imageNoMatches = root.findViewById(R.id.imageNoMatches);

        matchList = new ArrayList<>();

        if(matchStatus.equals("Finished"))matchList = FootballManiaContract.getDbHelperInstance(getActivity().getApplicationContext()).getMatchListPast();
        else if(matchStatus.equals("Live"))matchList = FootballManiaContract.getDbHelperInstance(getActivity().getApplicationContext()).getMatchListLive();
        else if (matchStatus.equals("Fixtures")) {
            matchList = FootballManiaContract.getDbHelperInstance(getActivity().getApplicationContext()).getMatchListUpcoming();
            Collections.reverse(matchList);
        }

        if(matchList.isEmpty()) {
            noMatches.setVisibility(View.VISIBLE);
            imageNoMatches.setVisibility(View.VISIBLE);
            noMatches.setText("No live matches right now");
        }
        else {
            noMatches.setVisibility(View.GONE);
            imageNoMatches.setVisibility(View.GONE);
        }

        homeIds = new ArrayList<>();
        awayIds = new ArrayList<>();

        for(int i=0;i<matchList.size();i++) {
            homeIds.add(matchList.get(i).getIdTeamHome());
            awayIds.add(matchList.get(i).getIdTeamAway());
        }

        listView = root.findViewById(R.id.matchesListView);
        listAdapter = new MatchListAdapter(FootballManiaContract.getDbHelperInstance(getActivity().getApplicationContext()), matchList, getActivity().getApplicationContext(), -1, homeIds, awayIds);
        listView.setAdapter(listAdapter);

        hideTextView = root.findViewById(R.id.hideTextView);
        hideTextView.setVisibility(View.GONE);

        loadTextView = root.findViewById(R.id.loadMoreTextView);
        loadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listAdapter.getMatchData().size()>0) {
                    if((itemsCounter+10)<matchList.size()) itemsCounter += 10;
                    else itemsCounter = matchList.size();
                    setListViewHeight(itemsCounter);
                    hideTextView.setVisibility(View.VISIBLE);
                }

            }
        });
        hideTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListViewHeight(10);
                itemsCounter=10;
                hideTextView.setVisibility(View.GONE);
            }
        });

        return root;
    }

    private void setListViewHeight(int itemCount){
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
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onResume() {
        //TODO: clear list and update from contract
        listAdapter.notifyDataSetChanged();
        super.onResume();
    }

    /*public void refresh(View view) {
        //ToDo: clear list and get from database

        //TUTAJ LEPIEJ DODAC ARGUMENT ID I USUNAC TYLKO TA DRUZYNE NIZ LECIEC PETLE
        ArrayList<Team> temp = new ArrayList<>();
        for(Team t : teamList){
            if(!t.getFavourite()){
                temp.add(t);
            }
        }
        teamList.removeAll(temp);
        listAdapter.notifyDataSetChanged();
    }
*/

}
