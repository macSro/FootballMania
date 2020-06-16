package com.footballmania.ui.favourites;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.ui.Adapters.FavouriteTeamsAdapter;
import com.footballmania.ui.TeamActivity;

import java.util.ArrayList;

import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;

public class FavouriteTeamsFragment extends Fragment {

    private final String ARG_NAME_TEAM_ID = "teamId";

    private ListView listView;

    private ArrayList<Team> teamList;

    private FavouriteTeamsAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite_teams, container, false);

        teamList = getDbHelperInstance(getContext()).getFavouriteTeams();

        listView = root.findViewById(R.id.favouriteTeamsListView);
        listAdapter = new FavouriteTeamsAdapter(teamList, getActivity().getApplicationContext());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra(ARG_NAME_TEAM_ID, listAdapter.getItem(i).getId());
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        teamList.clear();
        teamList.addAll(getDbHelperInstance(getContext()).getFavouriteTeams());
        listAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
