package com.zdvdev.steamgamesstats.datamodel;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
@SuppressWarnings("unchecked")
public class DataManager {


//	public static ISubscription getMtgCardList(final Context ctx, Fragment fragment, final OnJobStatusChangedListener<List<MtgCard>> listener) {
//		final Observable<List<MtgCard>> sourceObservable = Observable.create(new Observable.OnSubscribe<List<MtgCard>>() {
//			@Override public void call(Subscriber<? super List<MtgCard>> subscriber) {
//				subscriber.onNext(DBDataSource.getInstance(ctx).getMtgCardList());
//				subscriber.onCompleted();
//			}
//		});
//
//		CustomObserver<List<MtgCard>> observer = new CustomObserver<List<MtgCard>>(listener);
//		rx.Subscription subscription =
//				AndroidObservable.bindFragment(fragment, sourceObservable).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);
//
//		return new CustomSubscription(subscription);
//	}
}
