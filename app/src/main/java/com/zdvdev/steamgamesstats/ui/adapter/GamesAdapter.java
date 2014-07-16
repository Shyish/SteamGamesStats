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
import com.zdvdev.steamgamesstats.datamodel.model.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 05/06/14
 */
public class GamesAdapter extends BaseAdapter {
	private final LayoutInflater mInflater;
	private List<Game> mData;

	public GamesAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<Game> data) {
		mData = data;
		notifyDataSetChanged();
	}


	public void setData(Game[] games) {
		mData = new ArrayList<Game>(Arrays.asList(games));
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
	public Game getItem(int position) {
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

		Game item = getItem(position);

		h.mGameName.setText(item.getName());

		return convertView;
	}


	public static class ViewHolder {
		@InjectView(R.id.games_list_row_name) TextView mGameName;

		ViewHolder(View view) {ButterKnife.inject(this, view);}
	}
}

