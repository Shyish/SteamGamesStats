package com.zdvdev.steamgamesstats.presentation.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.common.TransformerProvider;
import com.zdvdev.steamgamesstats.data.CacheDataSource;
import com.zdvdev.steamgamesstats.data.SteamRepository;
import com.zdvdev.steamgamesstats.domain.model.UsersHoursPerGame;
import com.zdvdev.steamgamesstats.domain.usecase.GameListUseCase;
import com.zdvdev.steamgamesstats.presentation.presenter.UserSearchPresenter;
import com.zdvdev.steamgamesstats.presentation.ui.navigable.NavigableFragment;
import com.zdvdev.steamgamesstats.presentation.widget.RemovableEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 16/07/14
 */
public class UserSearchFragment extends NavigableFragment implements RemovableEditText.OnFieldRemovedListener,
      UserSearchPresenter.View {


    @BindView(R.id.games_list_send_button)
    Button gamesListSendButton;
    @BindView(R.id.games_list_fields_container)
    LinearLayout gamesListFieldsContainer;
    @BindView(R.id.loading_view)
    View loadingView;

    private final ArrayList<RemovableEditText> userFields = new ArrayList<>(2);

    //TODO replace with DI
    private final UserSearchPresenter presenter = new UserSearchPresenter(
          new GameListUseCase(SteamRepository.get(), new CacheDataSource(), TransformerProvider.getInstance())
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_search_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        setHasOptionsMenu(true);
        RemovableEditText mainUsernameField = findById(view, R.id.games_list_name_field);
        mainUsernameField.setRemovable(false);
        userFields.add(mainUsernameField);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_user) {
            RemovableEditText et = new RemovableEditText(getActivity());
            et.setOnFieldRemovedListener(this);
            et.setHint(R.string.steam_user_hint);
            userFields.add(et);
            gamesListFieldsContainer.addView(et);
            //TODO animate!
            et.findFocus();//TODO
        }
        return true;
    }

    @Override
    public void onFieldRemoved(RemovableEditText field) {
        userFields.remove(field);
        gamesListFieldsContainer.removeView(field);
    }

    @OnClick(R.id.games_list_send_button)
    public void onSendButtonClicked() {
        presenter.onSendButtonClicked();
    }

    @Override
    public boolean areAllFieldsFilled() {
        boolean ret = true;
        for (int i = 0, userFieldsSize = userFields.size(); i < userFieldsSize; i++) {
            RemovableEditText et = userFields.get(i);
            if (TextUtils.isEmpty(et.getText())) {
                et.setError(getString(R.string.error_field_required));
                ret = false;
            }
        }

        return ret;
    }

    @Override
    public void disableUiElements() {
        ButterKnife.apply(userFields, ENABLED, false);
        gamesListSendButton.setEnabled(false);
    }

    @Override
    public void enableUiElements() {
        ButterKnife.apply(userFields, ENABLED, true);
        gamesListSendButton.setEnabled(true);
    }

    @Override
    public void startLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        inputManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public ArrayList<String> getUserNames() {
        int size = userFields.size();
        ArrayList<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            RemovableEditText et = userFields.get(i);
            list.add(et.getText());
        }
        return list;
    }

    @Override
    public void openGamesListScreenWithCommonGames(ArrayList<UsersHoursPerGame> commonGames){
        navigateTo(GamesListFragment.class, Mode.ADD, GamesListFragment.buildParams(commonGames));
    }

        @Override
    public void showError(Throwable throwable) {
        //TODO difference between internet err/timeout, private profile and non-updated profile.
        throwable.printStackTrace();
        Toast.makeText(getActivity(), R.string.error_search_user, Toast.LENGTH_LONG).show();
    }

    private static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };
}
