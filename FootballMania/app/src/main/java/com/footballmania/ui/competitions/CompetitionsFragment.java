package com.footballmania.ui.competitions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.ui.Adapters.CompetitionsGridAdapter;

import java.util.ArrayList;

public class CompetitionsFragment extends Fragment {

    ArrayList<Competition> competitions;
    CompetitionsGridAdapter competitionsGridAdapter;
    GridView gridView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_competitions, container, false);

        gridView = root.findViewById(R.id.gridCompetitions);
        competitions = new ArrayList<>();
        competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        System.out.println(competitions);

        competitionsGridAdapter = new CompetitionsGridAdapter(getActivity().getApplicationContext(), competitions);
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
        competitionsGridAdapter.notifyDataSetChanged();
        super.onResume();
    }

}