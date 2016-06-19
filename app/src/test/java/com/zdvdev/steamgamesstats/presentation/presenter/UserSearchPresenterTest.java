package com.zdvdev.steamgamesstats.presentation.presenter;

import com.zdvdev.steamgamesstats.domain.model.Game;
import com.zdvdev.steamgamesstats.domain.model.User;
import com.zdvdev.steamgamesstats.domain.model.UsersHoursPerGame;
import com.zdvdev.steamgamesstats.domain.usecase.GameListUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@SuppressWarnings({"AccessStaticViaInstance", "unchecked"})
public class UserSearchPresenterTest {

    @Mock
    private GameListUseCase mockUseCase;
    private UserSearchPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = spy(new UserSearchPresenter(mockUseCase));
    }

    @Test
    public void collectHoursPerGameSortsGames() throws Exception {
        List<User> list = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        Game game1 = new Game();
        game1.setName("D");
        Game game2 = new Game();
        game2.setName("C");
        Game game3 = new Game();
        game3.setName("Z");
        games.add(game1);
        games.add(game2);
        games.add(game3);
        User user1 = new User();
        user1.setGames(games);
        list.add(user1);
        User user2 = new User();
        user2.setGames(games);
        list.add(user2);
        presenter.collectHoursPerGame(list);

        verify(presenter).appendOrRemoveUserHoursFromCommonGames(argThat(
              new ArgumentMatcher<ArrayList<UsersHoursPerGame>>() {
                  @Override
                  public boolean matches(Object argument) {
                      ArrayList<UsersHoursPerGame> usersHoursPerGames = (ArrayList<UsersHoursPerGame>) argument;
                      return "C".equals(usersHoursPerGames.get(0).game.getName())
                            && "D".equals(usersHoursPerGames.get(1).game.getName())
                            && "Z".equals(usersHoursPerGames.get(2).game.getName());
                  }
              }), any(User.class));
    }

    @Test
    public void collectHoursPerGameFillsListBeforeAppending() throws Exception {
        doNothing().when(presenter).appendOrRemoveUserHoursFromCommonGames(any(ArrayList.class), any(User.class));

        List<User> list = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        Game game1 = new Game();
        game1.setName("D");
        Game game2 = new Game();
        game2.setName("C");
        Game game3 = new Game();
        game3.setName("Z");
        games.add(game1);
        games.add(game2);
        games.add(game3);
        User user1 = mock(User.class);
        user1.setGames(games);
        list.add(user1);
        presenter.collectHoursPerGame(list);

        verify(user1).getUserHoursPerGame();
        verify(presenter, never()).appendOrRemoveUserHoursFromCommonGames(any(ArrayList.class), any(User.class));
    }

    @Test
    public void collectHoursPerGameAppends() throws Exception {
        doNothing().when(presenter).appendOrRemoveUserHoursFromCommonGames(any(ArrayList.class), any(User.class));

        List<User> list = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        Game game1 = new Game();
        game1.setName("D");
        Game game2 = new Game();
        game2.setName("C");
        Game game3 = new Game();
        game3.setName("Z");
        games.add(game1);
        games.add(game2);
        games.add(game3);
        User user1 = new User();
        user1.setGames(games);
        list.add(user1);
        User user2 = new User();
        user2.setGames(games);
        list.add(user2);
        presenter.collectHoursPerGame(list);

        verify(presenter).appendOrRemoveUserHoursFromCommonGames(any(ArrayList.class), eq(user2));
    }

    @Test
    public void appendOrRemoveUserHoursFromCommonGames() throws Exception {

    }
}