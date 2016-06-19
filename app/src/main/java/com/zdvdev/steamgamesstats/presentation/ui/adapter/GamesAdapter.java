package com.zdvdev.steamgamesstats.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.domain.model.UsersHoursPerGame;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 05/06/14
 */
public class GamesAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;

    private ArrayList<UsersHoursPerGame> mData;

    public GamesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public ArrayList<UsersHoursPerGame> getData() {
        return mData;
    }

    public void setData(ArrayList<UsersHoursPerGame> gamesList) {
        mData = gamesList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public UsersHoursPerGame getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.game_list_row_layout, parent, false);
            h = new ViewHolder(convertView);

            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        UsersHoursPerGame item = getItem(position);

        h.mGameName.setText(item.game.getName());
        Picasso.with(convertView.getContext()).load(item.game.getLogo()).placeholder(R.drawable.default_game_img)
              .into(h.mGameImage);

        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.games_list_row_name)
        TextView mGameName;
        @BindView(R.id.games_list_row_image)
        ImageView mGameImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

