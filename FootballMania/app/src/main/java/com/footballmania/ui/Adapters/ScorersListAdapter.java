package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.Scorer;

import java.util.ArrayList;

public class ScorersListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Scorer> scorers;
    LayoutInflater layoutInflater = null;

    public ScorersListAdapter(Context context, ArrayList<Scorer> scorers){
        this.context = context;
        this.scorers = scorers;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scorers.size();
    }

    @Override
    public Object getItem(int position) {
        return scorers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = layoutInflater.inflate(R.layout.fragment_competition_topscorers_list, null);

        TextView scorerPosition = (TextView) view.findViewById(R.id.position);
        TextView player = (TextView) view.findViewById(R.id.player);
        TextView goals = (TextView) view.findViewById(R.id.goals);
        TableRow row = view.findViewById(R.id.scorerRow);

        scorerPosition.setText(String.valueOf(position + 1));
        player.setText(scorers.get(position).getSeasonPlayerName());
        goals.setText(String.valueOf(scorers.get(position).getNumberOfGoals()));

        if(position%2==1) row.setBackgroundColor(view.getResources().getColor(R.color.colorItemLightGrey));



        return view;

    }
}