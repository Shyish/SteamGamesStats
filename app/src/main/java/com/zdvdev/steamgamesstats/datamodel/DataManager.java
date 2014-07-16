package com.zdvdev.steamgamesstats.datamodel;

import android.support.v4.app.Fragment;
import com.zdvdev.steamgamesstats.async.ISubscription;
import com.zdvdev.steamgamesstats.async.OnJobStatusChangedListener;
import com.zdvdev.steamgamesstats.datamodel.model.GamesList;
import com.zdvdev.steamgamesstats.datamodel.source.CloudDataSource;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
@SuppressWarnings("unchecked")
public class DataManager {

	public static ISubscription getGamesList(String username, Fragment fragment, OnJobStatusChangedListener<GamesList> listener) {
		return CloudDataSource.requestFromFragmentForObserver(fragment, listener, CloudDataSource.getApiManager().getGamesForUser(username));
	}
}
