package com.zdvdev.steamgamesstats.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.datamodel.model.wrapper.GameUsersWrapper;

import java.util.ArrayList;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 05/06/14
 */
public class GamesAdapter extends BaseAdapter {
	private final LayoutInflater mInflater;

	private ArrayList<GameUsersWrapper> mData;

	public GamesAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public ArrayList<GameUsersWrapper> getData() {
		return mData;
	}

	public void setData(ArrayList<GameUsersWrapper> gamesList) {
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
	public GameUsersWrapper getItem(int position) {
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

		GameUsersWrapper item = getItem(position);

		h.mGameName.setText(item.game.getName());

		return convertView;
	}

	public static class ViewHolder {
		@InjectView(R.id.games_list_row_name) TextView mGameName;

		ViewHolder(View view) {ButterKnife.inject(this, view);}
	}
}

