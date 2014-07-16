package com.zdvdev.steamgamesstats.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.async.OnJobStatusChangedListener;
import com.zdvdev.steamgamesstats.datamodel.DataManager;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.ui.adapter.GamesAdapter;
import com.zdvdev.steamgamesstats.ui.navigable.NavigableFragment;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 08/07/14
 */
public class GamesListFragment extends NavigableFragment implements OnJobStatusChangedListener<GamesList> {

	@InjectView(R.id.games_list) ListView mGamesList;
	@InjectView(R.id.games_list_name_field) EditText mGamesListNameField;
	@InjectView(R.id.games_list_send_button) Button mGamesListSendButton;
	private GamesAdapter mAdapter;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.games_list_layout, container, false);
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mAdapter = new GamesAdapter(getActivity());
		mGamesList.setAdapter(mAdapter);
	}

	@OnClick(R.id.games_list_send_button)
	public void onSendButtonClicked() {
		String steamUsername = mGamesListNameField.getText().toString();
		if (TextUtils.isEmpty(steamUsername)) {
			mGamesListNameField.setError(getString(R.string.error_field_required));
		} else {
			DataManager.getGamesList(steamUsername, this, this);
		}
	}

	@Override public void onCompleted(GamesList gamesList) {
		mAdapter.setData(gamesList.getGames());
		mGamesList.setVisibility(View.VISIBLE);
		mGamesListNameField.setVisibility(View.GONE);
		mGamesListSendButton.setVisibility(View.GONE);
	}

	@Override public void onUpdate(int progress) {}

	@Override public void onError(Throwable throwable) {
		//TODO
		throwable.printStackTrace();
	}
}
