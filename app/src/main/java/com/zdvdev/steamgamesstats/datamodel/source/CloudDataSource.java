package com.zdvdev.steamgamesstats.datamodel.source;

import android.support.v4.app.Fragment;
import com.zdvdev.steamgamesstats.async.CustomObserver;
import com.zdvdev.steamgamesstats.async.CustomSubscription;
import com.zdvdev.steamgamesstats.async.ISubscription;
import com.zdvdev.steamgamesstats.async.OnJobStatusChangedListener;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.datamodel.source.converter.SimpleXMLConverter;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class CloudDataSource {
	// The base API endpoint.
	private static final String SERVER_URL = "http://steamcommunity.com";

	// Url params
//	static final String PARAM_GAME_NAME = "gamename";
	static final String PARAM_USER_NAME = "username";
	static final String PARAM_ID = "id";

	static final String FORMAT = "&xml=1";

	// API methods
	static final String URI_GET_ID = "/actions/Search?T=Account&K=%22{" + PARAM_USER_NAME + "}%22"; //view-source:steamcommunity.com/actions/Search?T=Account&K="javier"
	static final String URI_GET_PROFILE = "/id/{" + PARAM_USER_NAME + "}/?" + FORMAT;
	static final String URI_GET_GAMES_FROM_ID = "/profile/{" + PARAM_ID + "}/games?tab=all" + FORMAT;
	static final String URI_GET_GAMES_FROM_USER = "/id/{" + PARAM_USER_NAME + "}/games?tab=all" + FORMAT;
	//TODO build the url or extract from the 'statsLink's Game param
//	static final String URI_GET_ACHIEVEMENTS_FOR_GAME_FROM_USER = "/id/{" + PARAM_USER_NAME + "}/stats/{" + PARAM_GAME_NAME + "}?"+ FORMAT;

	private static RestAdapter mRestAdapter;
	private static ApiManagerService mApiManager;

	private static RestAdapter getRestAdapter() {
		if (mRestAdapter == null) {
			mRestAdapter = new RestAdapter.Builder().setEndpoint(SERVER_URL).setConverter(new SimpleXMLConverter()).build();
		}
		return mRestAdapter;
	}

	public static ApiManagerService getApiManager() {
		if (mApiManager == null) {
			mApiManager = getRestAdapter().create(ApiManagerService.class);
		}
		return mApiManager;
	}

	public interface ApiManagerService {
		@GET(URI_GET_GAMES_FROM_USER) Observable<GamesList> getGamesForUser(@Path(PARAM_USER_NAME) String user);
		@GET(URI_GET_GAMES_FROM_ID) Observable<GamesList> getGamesForUser(@Path(PARAM_ID) long user);
	}

	/**
	 * Generic method to make requests
	 *
	 * @param fragment
	 * 		The calling fragment (to avoid responses when the fragment is being dettached/destroyed)
	 * @param listener
	 * 		Response listener
	 * @param sourceObservable
	 * 		The #ApiManagerService method to call
	 */
	public static <T> ISubscription requestFromFragmentForObserver(Fragment fragment, OnJobStatusChangedListener<T> listener,
																   Observable<T> sourceObservable) {

		CustomObserver<T> observer = new CustomObserver<T>(listener);
		rx.Subscription subscription;
		Observable<T> ob = (fragment != null) ? AndroidObservable.fromFragment(fragment, sourceObservable) : sourceObservable;

		subscription = ob.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);

		return new CustomSubscription(subscription);
	}

}
