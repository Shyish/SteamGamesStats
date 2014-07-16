package com.zdvdev.steamgamesstats.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.async.OnJobStatusChangedListener;
import com.zdvdev.steamgamesstats.datamodel.DataManager;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.ui.navigable.NavigableFragment;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 16/07/14
 */
public class UserSearchFragment extends NavigableFragment implements OnJobStatusChangedListener<GamesList> {


	@InjectView(R.id.games_list_name_field) EditText mGamesListNameField;
	@InjectView(R.id.games_list_send_button) Button mGamesListSendButton;
	@InjectView(R.id.loading_view) View mLoadingView;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_search_layout, container, false);
	}

	@OnClick(R.id.games_list_send_button)
	public void onSendButtonClicked() {
		String steamUsername = mGamesListNameField.getText().toString();
		if (TextUtils.isEmpty(steamUsername)) {
			mGamesListNameField.setError(getString(R.string.error_field_required));
		} else {
			mGamesListNameField.setEnabled(false);
			mGamesListSendButton.setEnabled(false);
			mLoadingView.setVisibility(View.VISIBLE);
			InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			DataManager.getGamesList(steamUsername, this, this);
		}
	}

	@Override public void onResume() {
		super.onResume();
		mGamesListNameField.findFocus();
	}

	@Override public void onCompleted(GamesList gamesList) {
		mGamesListNameField.setEnabled(true);
		mGamesListSendButton.setEnabled(true);
		mLoadingView.setVisibility(View.GONE);

		Bundle params = new Bundle();
		params.putSerializable(GamesListFragment.KEY_GAMES, gamesList);
		navigateTo(GamesListFragment.class, Mode.ADD, params);
	}

	@Override public void onUpdate(int progress) {}

	@Override public void onError(Throwable throwable) {
		//TODO
		throwable.printStackTrace();
		mGamesListNameField.setEnabled(true);
		mGamesListSendButton.setEnabled(true);
		mLoadingView.setVisibility(View.GONE);
	}
}
