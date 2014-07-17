package com.zdvdev.steamgamesstats.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.ui.adapter.GamesAdapter;
import com.zdvdev.steamgamesstats.ui.navigable.NavigableFragment;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 08/07/14
 */
public class GamesListFragment extends NavigableFragment {

	public static final String KEY_GAMES = "games";
	@InjectView(R.id.games_list) ListView mGamesList;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.games_list_layout, container, false);
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		GamesAdapter adapter = new GamesAdapter(getActivity());
		mGamesList.setAdapter(adapter);

		if (getArguments() != null && getArguments().containsKey(KEY_GAMES)) {
			GamesList gamesList = (GamesList) getArguments().getSerializable(KEY_GAMES);

			//TODO sorting by name or playtime

			adapter.setData(gamesList.getGames());
		} else {
			Toast.makeText(getActivity(), R.string.error_nodata, Toast.LENGTH_LONG).show();
			navigateUp();
		}

	}

//	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.search_menu, menu);
//	}
//
//	@Override public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_sort) {
//			item.setIcon(R.drawable.)
//		}
//		return true;
//	}

}
