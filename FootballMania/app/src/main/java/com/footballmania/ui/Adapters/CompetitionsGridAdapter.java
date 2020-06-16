package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.utils.Utility;
import com.footballmania.database.dbobjects.Competition;

import java.util.ArrayList;

public class CompetitionsGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Competition> competitions;
    LayoutInflater inflater;

    public CompetitionsGridAdapter(Context context, ArrayList<Competition> competitions){
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.competition, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageCompetition);
        TextView textView = (TextView) view.findViewById(R.id.competitionName);

        String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);
        textView.setText(competitions.get(position).getName());

        return view;

    }
}