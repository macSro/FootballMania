package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Team;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

public class FavouriteTeamsAdapter extends ArrayAdapter {

    private ArrayList<Team> teamData;
    Context mContext;

    private class ViewHolder{
        ImageView logo;
        TextView teamName;
        ImageButton favouriteButton;
    }

    public FavouriteTeamsAdapter(ArrayList<Team> teamData, Context context){
        super(context, R.layout.row_universal, teamData);
        this.teamData = teamData;
        this.mContext = context;
    }

    @Nullable
    @Override
    public Team getItem(int position) {
        return teamData.get(position);
    }

    public View getView(final int position, View convertView, final ViewGroup parent){
        final Team team = getItem(position);

        final FavouriteTeamsAdapter.ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new FavouriteTeamsAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_universal, parent, false);

            viewHolder.teamName = convertView.findViewById(R.id.row_name);
            viewHolder.favouriteButton = convertView.findViewById(R.id.row_favourite);
            viewHolder.logo = convertView.findViewById(R.id.row_logo);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (FavouriteTeamsAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.teamName.setText(team.getName());

        viewHolder.favouriteButton.setImageResource(R.drawable.ic_favorite_primary_24dp);

        viewHolder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FootballManiaContract.getDbHelperInstance(getContext()).deleteFromFavouriteTeams(team.getId());
                teamData.remove(position);
                notifyDataSetChanged();
            }
        });

        String s = Utility.getTeamLogoFleName(team.getName());
        int resID = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        viewHolder.logo.setImageResource(resID);

        return convertView;
    }
}