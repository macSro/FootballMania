package com.footballmania.ui.favourites;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.ui.Adapters.CompetitionsGridAdapter;
import com.footballmania.ui.Adapters.FavouriteCompetitionsGridAdapter;
import com.footballmania.ui.competitions.CompetitionActivity;

import java.util.ArrayList;

import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;


public class FavouriteCompetitionsFragment extends Fragment {

    private GridView gridView;

    private FavouriteCompetitionsGridAdapter competitionsGridAdapter;

    private ArrayList<Competition> competitions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourite_competitions, container, false);

        gridView = root.findViewById(R.id.gridFavouriteCompetitions);

        competitions = new ArrayList<>();

        //competitions = FootballManiaContract.getDbHelperInstance(getContext()).getFavouriteCompetitionsList();
        competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        competitionsGridAdapter = new FavouriteCompetitionsGridAdapter(getActivity().getApplicationContext(), competitions);
        gridView.setAdapter(competitionsGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Competition c = competitions.get(i);
                Intent intent = new Intent(getActivity(), CompetitionActivity.class);
                intent.putExtra(CompetitionActivity.ARG_COMPETITION_ID, c.getId());
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        competitions.clear();
        competitions.addAll(getDbHelperInstance(getContext()).getFavouriteCompetitions());
        competitionsGridAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
