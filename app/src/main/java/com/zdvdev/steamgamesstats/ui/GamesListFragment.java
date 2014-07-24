package com.zdvdev.steamgamesstats.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.datamodel.model.wrapper.GameUsersWrapper;
import com.zdvdev.steamgamesstats.ui.adapter.GamesAdapter;
import com.zdvdev.steamgamesstats.ui.navigable.NavigableFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 08/07/14
 */
public class GamesListFragment extends NavigableFragment {

	public static final String KEY_GAMES = "games";
	@InjectView(R.id.games_list) ListView mGamesList;
	@InjectView(R.id.games_list_total) TextView mGamesListTotal;

	private enum Sorting {
		ALPHABETICALLY(android.R.drawable.ic_menu_sort_alphabetically, R.string.sort_alphabetically),
		PLAYTIME(android.R.drawable.ic_menu_sort_by_size, R.string.sort_size);

		public final int mIconRes;
		public final int mTitleRes;

		Sorting(int iconRes, int titleRes) {
			mIconRes = iconRes;
			mTitleRes = titleRes;
		}

		public Sorting next() {
			Sorting[] values = Sorting.values();
			return values[(this.ordinal() + 1) % values.length];
		}
	}

	private Sorting currentSorting = Sorting.ALPHABETICALLY;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.games_list_layout, container, false);
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setHasOptionsMenu(true);

		GamesAdapter adapter = new GamesAdapter(getActivity());
		mGamesList.setAdapter(adapter);

		Bundle args = getArguments();
		if (args != null && args.containsKey(KEY_GAMES)) {
			ArrayList<GameUsersWrapper> gamesList = (ArrayList<GameUsersWrapper>) args.getSerializable(KEY_GAMES);
			adapter.setData(gamesList);
			doSort();

			//TODO maybe add some extra info in a top bar like:
			// Total hours per player (SU, MU)
			mGamesListTotal.setText(String.format(getString(R.string.games_list_total), gamesList.size()));
		} else {
			Toast.makeText(getActivity(), R.string.error_nodata, Toast.LENGTH_LONG).show();
			navigateUp();
		}
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.list_menu, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_sort) {
			currentSorting = currentSorting.next();
			Sorting nextSorting = currentSorting.next();
			item.setIcon(nextSorting.mIconRes);
			item.setTitle(nextSorting.mTitleRes);
			doSort();
		}

		return true;
	}

	private void doSort() {
		GamesAdapter adapter = (GamesAdapter) mGamesList.getAdapter();
		switch (currentSorting) {

			case ALPHABETICALLY:
				Collections.sort(adapter.getData(), gameNameComparator);
				break;
			case PLAYTIME:
				Collections.sort(adapter.getData(), gamePlayTimeComparator);
				break;
		}

		adapter.notifyDataSetChanged();

	}

	private static final Comparator<GameUsersWrapper> gameNameComparator = new Comparator<GameUsersWrapper>() {
		@Override public int compare(GameUsersWrapper lhs, GameUsersWrapper rhs) {
			return lhs.game.getName().compareTo(rhs.game.getName());
		}
	};

	private static final Comparator<GameUsersWrapper> gamePlayTimeComparator = new Comparator<GameUsersWrapper>() {
		@Override public int compare(GameUsersWrapper lhs, GameUsersWrapper rhs) {
			// Descending order
			return rhs.usersHours.get(0).second.compareTo(lhs.usersHours.get(0).second);
		}
	};

}
