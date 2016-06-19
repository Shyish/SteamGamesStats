package com.zdvdev.steamgamesstats.data;

import com.zdvdev.steamgamesstats.domain.model.User;

import rx.Observable;

public interface GameListRemoteRepository {
    Observable<User> getGameList(String userName);
}
