package com.zdvdev.steamgamesstats.data;

import android.support.v4.util.LruCache;

public class CacheDataSource implements GameListLocalRepository{

	private static final int CACHE_SIZE = 4 * 1024 * 1024; // 4MB
	static final LruCache<String, Object> REQUEST_CACHE = new LruCache<String, Object>(CACHE_SIZE);

	public static Object get(String tag) {
		return REQUEST_CACHE.get(tag);
	}

//	/**
//	 * A wrapper for the job status listener which uses an internal memory cache to store the requests/results
//	 * @param <T> The response type
//	 */
//	public static class CachedOnJobFinishedListenerWrapper<T> implements OnJobStatusChangedListener<T> {
//		private final String mTag;
//		private final OnJobStatusChangedListener mListener;
//
//		public CachedOnJobFinishedListenerWrapper(String tag, OnJobStatusChangedListener listener) {
//			mTag = tag;
//			mListener = listener;
//		}
//
//		@Override public void onCompleted(T result) {
//			mListener.onCompleted(result);
//			REQUEST_CACHE.put(mTag, result);
//		}
//
//		@Override public void onUpdate(int progress) {
//			mListener.onUpdate(progress);
//		}
//
//		@Override public void onError(Throwable throwable) {
//			mListener.onError(throwable);
//		}
//	}
}
