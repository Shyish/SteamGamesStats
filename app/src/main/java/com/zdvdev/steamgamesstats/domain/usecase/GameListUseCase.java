package com.zdvdev.steamgamesstats.domain.usecase;

import com.zdvdev.steamgamesstats.common.TransformerProvider;
import com.zdvdev.steamgamesstats.data.GameListLocalRepository;
import com.zdvdev.steamgamesstats.data.GameListRemoteRepository;
import com.zdvdev.steamgamesstats.domain.model.User;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.FuncN;

public class GameListUseCase {

    private final GameListRemoteRepository remoteRepository;
    private final GameListLocalRepository localRepository;
    private final TransformerProvider transformerProvider;

    public GameListUseCase(GameListRemoteRepository remoteRepository, GameListLocalRepository localRepository,
          TransformerProvider transformerProvider) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        this.transformerProvider = transformerProvider;
    }

    public Observable<List<User>> getGameList(List<String> userNames) {
        ArrayList<Observable<User>> list = new ArrayList<>();
        for (int i = 0, userNamesSize = userNames.size(); i < userNamesSize; i++) {
            list.add(remoteRepository.getGameList(userNames.get(i)));
        }

        return Observable.zip(list, new FuncN<List<User>>() {
            @Override
            public List<User> call(Object... args) {
                int length = args.length;
                ArrayList<User> ret = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    ret.add((User) args[i]);
                }
                return ret;
            }
        }).compose(transformerProvider.<List<User>>ioTransformer());
    }
}
