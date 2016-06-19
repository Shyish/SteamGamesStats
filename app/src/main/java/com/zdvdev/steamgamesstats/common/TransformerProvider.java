package com.zdvdev.steamgamesstats.common;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Provides observable transformers that simplify the usage of {@linkplain Observable#subscribeOn(Scheduler)}
 * and {@linkplain Observable#observeOn(Scheduler)} methods.
 */
public class TransformerProvider {
    final Scheduler io;
    final Scheduler immediate;
    final Scheduler computation;
    final Scheduler mainThread;

    //TODO Hacky methods due to lack of DI
    public static TransformerProvider getInstance() {
        return new TransformerProvider(Schedulers.io(), Schedulers.immediate(), Schedulers.computation(),
              Schedulers.newThread());
    }

    public static TransformerProvider getTestInstace() {
        return new TransformerProvider(Schedulers.immediate(), Schedulers.immediate(), Schedulers.immediate(),
              Schedulers.immediate());
    }

    protected TransformerProvider(Scheduler io,
          Scheduler immediate,
          Scheduler computation,
          Scheduler mainThread) {
        this.io = io;
        this.immediate = immediate;
        this.computation = computation;
        this.mainThread = mainThread;
    }

    /**
     * Simplification for the subscribeOn and observeOn methods, subscribing on {@linkplain Schedulers#io()}.
     * See {@linkplain #transformer(Scheduler)}.
     */
    public <T> Observable.Transformer<T, T> ioTransformer() {
        return transformer(io);
    }

    /**
     * Simplification for the subscribeOn and observeOn methods, subscribing on {@linkplain Schedulers#computation()}.
     * See {@linkplain #transformer(Scheduler)}.
     */
    public <T> Observable.Transformer<T, T> immediateTransformer() {
        return transformer(immediate);
    }

    /**
     * Simplification for the subscribeOn and observeOn methods, subscribing on {@linkplain Schedulers#immediate()}.
     * See {@linkplain #transformer(Scheduler)}.
     */
    public <T> Observable.Transformer<T, T> computationTransformer() {
        return transformer(computation);
    }

    /**
     * Simplification for the subscribeOn and observeOn method. See the example below:
     *
     * <pre><code>
     * Observable<NewsFeed> streamCachedFeed = streamCachedFeed();
     * streamCachedFeed.subscribeOn(Schedulers.io())
     *                  .observeOn(AndroidSchedulers.mainThread())
     *                  .subscribe();
     *
     *                  becomes ->
     *
     * Observable<NewsFeed> streamCachedFeed = streamCachedFeed();
     * streamCachedFeed.compose(transformers.transformer(backgroundScheduler))
     *                  .subscribe();
     * </code></pre>
     */
    public <T> Observable.Transformer<T, T> transformer(final Scheduler scheduler) {
        return new Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(scheduler).observeOn(mainThread);
            }
        };
    }
}
