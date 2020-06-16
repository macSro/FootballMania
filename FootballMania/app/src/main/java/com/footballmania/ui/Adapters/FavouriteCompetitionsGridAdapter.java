package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

public class FavouriteCompetitionsGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Competition> competitions;
    LayoutInflater inflater;

    public FavouriteCompetitionsGridAdapter(Context context, ArrayList<Competition> competitions){
        this.context = context;
        this.competitions = competitions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return competitions.size();
    }

    @Override
    public Object getItem(int position) {
        return competitions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = inflater.inflate(R.layout.favourite_competition, null);

        final ImageView imageButton = view.findViewById(R.id.imageButtonFavourite);
        ImageView imageView = view.findViewById(R.id.imageFavouriteCompetition);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FootballManiaContract.getDbHelperInstance(context).deleteFromFavouriteCompetitions(competitions.get(position).getId());
                competitions.remove(position);
                notifyDataSetChanged();
            }
        });

        String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);

        return view;

    }
}