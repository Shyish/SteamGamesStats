package com.zdvdev.steamgamesstats.presentation.presenter;

import android.util.Pair;

import com.zdvdev.steamgamesstats.domain.model.Game;
import com.zdvdev.steamgamesstats.domain.model.User;
import com.zdvdev.steamgamesstats.domain.model.UsersHoursPerGame;
import com.zdvdev.steamgamesstats.domain.usecase.GameListUseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static java.util.Collections.sort;

@SuppressWarnings("MethodMayBeStatic")
public class UserSearchPresenter {

    private View view;
    private final GameListUseCase useCase;

    public UserSearchPresenter(GameListUseCase useCase) {
        this.useCase = useCase;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void onSendButtonClicked() {
        if (view.areAllFieldsFilled()) {
            view.disableUiElements();
            view.startLoading();
            view.hideKeyboard();
            List<String> userNames = view.getUserNames();
            useCase.getGameList(userNames)
                  .doOnTerminate(new Action0() {
                      @Override
                      public void call() {
                          view.enableUiElements();
                          view.stopLoading();
                      }
                  })
                  .map(new Func1<List<User>, ArrayList<UsersHoursPerGame>>() {
                      @Override
                      public ArrayList<UsersHoursPerGame> call(List<User> users) {
                          return collectHoursPerGame(users);
                      }
                  })
                  .subscribe(new Action1<ArrayList<UsersHoursPerGame>>() {
                      @Override
                      public void call(ArrayList<UsersHoursPerGame> o) {
                          view.openGamesListScreenWithCommonGames(o);
                      }
                  }, new Action1<Throwable>() {
                      @Override
                      public void call(Throwable throwable) {
                          view.showError(throwable);
                      }
                  });
        }
    }

    private static final Comparator<Game> GAME_NAME_COMPARATOR = new Comparator<Game>() {
        @Override
        public int compare(Game lhs, Game rhs) {
            return lhs.compareToByName(rhs);
        }
    };

    protected ArrayList<UsersHoursPerGame> collectHoursPerGame(List<User> users) {
        ArrayList<UsersHoursPerGame> commonGames = new ArrayList<>(50);

        for (int i = 0, usersSize = users.size(); i < usersSize; i++) {
            User user = users.get(i);

            // Order the games list by their name in order to cross lists quickly
            sort(user.getGames(), GAME_NAME_COMPARATOR);

            // Fill up the list
            if (commonGames.isEmpty()) {
                commonGames = user.getUserHoursPerGame();

            } else {
                appendOrRemoveUserHoursFromCommonGames(commonGames, user);
            }
        }

        return commonGames;
    }

    protected void appendOrRemoveUserHoursFromCommonGames(ArrayList<UsersHoursPerGame> commonGames, User user) {
        for (int j = commonGames.size() - 1; j >= 0; j--) {
            UsersHoursPerGame usersHoursPerGame = commonGames.get(j);
            boolean isCommonGame = false;
            List<Game> games = user.getGames();

            for (int k = games.size() - 1; k >= 0; k--) {
                Game game = games.get(k);

                if (usersHoursPerGame.game.getAppID() == game.getAppID()) {
                    isCommonGame = true;
                    usersHoursPerGame.usersHours.add(new Pair<>(user.getSteamID(), game.getHoursOnRecord()));
                    break;
                } else if (usersHoursPerGame.game.compareToByName(game) > 0) {
                    // Since our 'gameList' list is sorted by game name, we can stop searching if the current game
                    // would be positioned above
                    isCommonGame = false;
                    break;
                }
            }
            if (!isCommonGame) {
                commonGames.remove(j);
            }
        }
    }

    public interface View {

        boolean areAllFieldsFilled();

        void disableUiElements();

        void hideKeyboard();

        List<String> getUserNames();

        void enableUiElements();

        void startLoading();

        void stopLoading();

        void openGamesListScreenWithCommonGames(ArrayList<UsersHoursPerGame> commonGames);

        void showError(Throwable throwable);
    }
}
