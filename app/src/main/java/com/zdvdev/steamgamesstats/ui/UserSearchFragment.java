package com.zdvdev.steamgamesstats.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.async.OnJobStatusChangedListener;
import com.zdvdev.steamgamesstats.datamodel.DataManager;
import com.zdvdev.steamgamesstats.datamodel.model.BasicGame;
import com.zdvdev.steamgamesstats.datamodel.model.Game;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.ui.navigable.NavigableFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 16/07/14
 */
public class UserSearchFragment extends NavigableFragment implements OnJobStatusChangedListener<GamesList> {

	// Game -> List(User - Playtime)
	private final HashMap<BasicGame, ArrayList<Pair<String, Float>>> commonGames = new HashMap<BasicGame, ArrayList<Pair<String, Float>>>();

	@InjectView(R.id.games_list_send_button) Button mGamesListSendButton;
	@InjectView(R.id.games_list_fields_container) LinearLayout mGamesListFieldsContainer;
	@InjectView(R.id.loading_view) View mLoadingView;

	private final ArrayList<EditText> userFields = new ArrayList<EditText>();
	private int remainingRequests;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_search_layout, container, false);
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setHasOptionsMenu(true);
		userFields.add((EditText) view.findViewById(R.id.games_list_name_field));
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_menu, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_user) {
			EditText et = new EditText(getActivity());
			et.setHint(R.string.steam_user_hint);
			userFields.add(et);
			mGamesListFieldsContainer.addView(et);
			//TODO animate!
			et.findFocus();//TODO
		}
		return true;
	}


	@OnClick(R.id.games_list_send_button)
	public void onSendButtonClicked() {
		if (allFieldsFilled()) {

			ButterKnife.apply(userFields, ENABLED, false);
			mGamesListSendButton.setEnabled(false);
			mLoadingView.setVisibility(View.VISIBLE);
			InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			commonGames.clear();
			remainingRequests = userFields.size();
			for (EditText et : userFields) {
				DataManager.getGamesList(et.getText().toString(), this, this);
			}
		}
	}

	private boolean allFieldsFilled() {
		boolean ret = true;
		for (EditText et : userFields) {
			if (TextUtils.isEmpty(et.getText().toString())) {
				et.setError(getString(R.string.error_field_required));
				ret = false;
			}
		}

		return ret;
	}

	private static final Comparator<Game> gameIdComparator = new Comparator<Game>() {
		@Override public int compare(Game lhs, Game rhs) {

			return lhs.compareTo(rhs);
		}
	};

	@Override public synchronized void onCompleted(GamesList gamesList) {
		// First of all, order the games list by their ids in order to cross lists quickly
		Collections.sort(gamesList.getGames(), gameIdComparator);

		//TODO
		if (commonGames.isEmpty()) {
			for (Game game : gamesList.getGames()) {
				ArrayList<Pair<String, Float>> users = new ArrayList<Pair<String, Float>>();
				users.add(new Pair<String, Float>(gamesList.getSteamID(), game.getHoursOnRecord()));

				commonGames.put(game, users);
			}
		}

		//TODO cross lists

		for (Iterator<BasicGame> iterator = commonGames.keySet().iterator(); iterator.hasNext(); ) {
			BasicGame bgame = iterator.next();
			boolean gameMatch = false;
			List<Game> games = gamesList.getGames();
			for (int i = 0, gamesSize = games.size(); i < gamesSize && !gameMatch; i++) {
				Game game = games.get(i);
				if (bgame.getAppID() == game.getAppID()) {
					gameMatch = true;
				} else if (bgame.getAppID() > game.getAppID()) {
					// Since our 'gameList' list is ordered by the appID, we can skip the rest if the searched one is greater
					break;
				}
			}
			if (!gameMatch) {
				iterator.remove();
			}
		}


		if (--remainingRequests == 0) {
			Bundle params = new Bundle();
			params.putSerializable(GamesListFragment.KEY_GAMES, gamesList);
			navigateTo(GamesListFragment.class, Mode.ADD, params);

			ButterKnife.apply(userFields, ENABLED, true);
			mGamesListSendButton.setEnabled(true);
			mLoadingView.setVisibility(View.GONE);
		}
	}

	@Override public void onUpdate(int progress) {}

	@Override public void onError(Throwable throwable) {
		//TODO
		throwable.printStackTrace();
		ButterKnife.apply(userFields, ENABLED, true);
		mGamesListSendButton.setEnabled(true);
		mLoadingView.setVisibility(View.GONE);
	}

	private static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
		@Override public void set(View view, Boolean value, int index) {
			view.setEnabled(value);
		}
	};
}
