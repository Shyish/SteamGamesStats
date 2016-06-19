package com.zdvdev.steamgamesstats.data;

import com.zdvdev.steamgamesstats.domain.model.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public final class SteamRepository implements GameListRemoteRepository {
    private static final SteamRepository INSTANCE = new SteamRepository();

    // The base API endpoint.
    private static final String SERVER_URL = "http://steamcommunity.com";

    // Url params
//	static final String PARAM_GAME_NAME = "gamename";
    static final String PARAM_USER_NAME = "username";
    static final String PARAM_ID = "id";

    static final String FORMAT = "&xml=1";

    // API methods
    static final String URI_GET_ID = "actions/Search?T=Account&K=%22{" + PARAM_USER_NAME + "}%22";
    //view-source:steamcommunity.com/actions/Search?T=Account&K="javier"
    static final String URI_GET_PROFILE = "id/{" + PARAM_USER_NAME + "}/?" + FORMAT;
    static final String URI_GET_GAMES_FROM_ID = "profile/{" + PARAM_ID + "}/games?tab=all" + FORMAT;
    static final String URI_GET_GAMES_FROM_USER = "id/{" + PARAM_USER_NAME + "}/games?tab=all" + FORMAT;
    //TODO build the url or extract from the 'statsLink's Game param
//	static final String URI_GET_ACHIEVEMENTS_FOR_GAME_FROM_USER = "id/{" + PARAM_USER_NAME + "}/stats/{" +
// PARAM_GAME_NAME + "}?"+ FORMAT;

    private static SteamService apiManager;

    private SteamRepository() { }

    public static SteamRepository get() {
        if (apiManager == null) {
            Retrofit restAdapter = new Retrofit.Builder()
                  .baseUrl(SERVER_URL)
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                  .addConverterFactory(SimpleXmlConverterFactory.create()).build();
            apiManager = restAdapter.create(SteamService.class);
        }

        return INSTANCE;
    }

    @Override
    public Observable<User> getGameList(String userName) {
        return apiManager.getGamesForUser(userName);
    }

    public interface SteamService {
        @GET(URI_GET_GAMES_FROM_USER)
        Observable<User> getGamesForUser(@Path(PARAM_USER_NAME) String user);

        @GET(URI_GET_GAMES_FROM_ID)
        Observable<User> getGamesForUser(@Path(PARAM_ID) long user);
    }
}
