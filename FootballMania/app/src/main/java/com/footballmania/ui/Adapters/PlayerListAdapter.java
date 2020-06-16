package com.footballmania.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.Player;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class PlayerListAdapter extends ArrayAdapter {

    private ArrayList<Player> playerData;
    Context context;

    private class ViewHolder{
        ImageView country;
        TextView name;
    }

    public PlayerListAdapter(ArrayList<Player> playerData, Context context){
        super(context, R.layout.row_player, playerData);
        this.playerData = playerData;
        this.context = context;
    }

    @Nullable
    @Override
    public Player getItem(int position) {
        return playerData.size()>0?playerData.get(position):null;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){
        final Player player = getItem(position);

        final PlayerListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new PlayerListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_player, parent, false);

            viewHolder.country = convertView.findViewById(R.id.player_country);
            viewHolder.name = convertView.findViewById(R.id.player_name);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (PlayerListAdapter.ViewHolder) convertView.getTag();
        }

        if(player!=null) {

            String s =  Utility.getTeamLogoFleName(player.getArea());
            int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
            viewHolder.country.setImageResource(resID);

            viewHolder.name.setText(player.getName());
        }

        return convertView;
    }
}
