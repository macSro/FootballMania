package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.utils.Utility;
import com.footballmania.database.dbobjects.Team;

import java.util.ArrayList;

import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;

public class TeamListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Team> teams;
    LayoutInflater layoutInflater = null;

    public TeamListAdapter(Context context, ArrayList<Team> teams){
        this.context = context;
        this.teams = teams;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Team getItem(int position) {
        return teams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Team team = teams.get(position);
        final TeamItemViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.team_list_item, null);
            viewHolder = new TeamItemViewHolder();
            viewHolder.teamLogo = convertView.findViewById(R.id.teamLogo);
            viewHolder.teamName = convertView.findViewById(R.id.teamName);
            viewHolder.layout = convertView.findViewById(R.id.teamLayout);
            viewHolder.favourite = convertView.findViewById(R.id.imageView13);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (TeamItemViewHolder) convertView.getTag();
        }

        viewHolder.teamName.setText(team.getName());

        if(position%2==0) viewHolder.layout.setBackgroundColor(convertView.getResources().getColor(R.color.colorItemLightGrey));
        else viewHolder.layout.setBackgroundColor(convertView.getResources().getColor(R.color.colorItemLight));

        String s = Utility.getTeamLogoFleName(team.getName());
        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
        if(resID != 0)
            viewHolder.teamLogo.setImageResource(resID);

        boolean isFavourite = FootballManiaContract.getDbHelperInstance(context).isFavouriteTeam(team.getId());
        if(isFavourite) viewHolder.favourite.setImageResource(R.drawable.ic_favorite_primary_24dp);
        else viewHolder.favourite.setImageResource(R.drawable.ic_favorite_border_primary_24dp);

        viewHolder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavourite = FootballManiaContract.getDbHelperInstance(context).isFavouriteTeam(team.getId());
                if(isFavourite){
                    viewHolder.favourite.setImageResource(R.drawable.ic_favorite_border_primary_24dp);
                    getDbHelperInstance(context).deleteFromFavouriteTeams(team.getId());
                }
                else{
                    viewHolder.favourite.setImageResource(R.drawable.ic_favorite_primary_24dp);
                    getDbHelperInstance(context).insertFavouriteTeam(team.getId());
                }
            }
        });

        return convertView;
    }

    class TeamItemViewHolder {
        ImageView teamLogo;
        TextView teamName;
        ConstraintLayout layout;
        ImageView favourite;
    }
}
