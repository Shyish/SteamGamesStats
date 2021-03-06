package com.zdvdev.steamgamesstats.ui.navigable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.zdvdev.steamgamesstats.common.INavigable;
import com.zdvdev.steamgamesstats.ui.base.BaseFragment;

import static com.zdvdev.steamgamesstats.common.AnimationUtils.AnimationType;

public abstract class NavigableFragment extends BaseFragment implements INavigable {

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private INavigable mNavigableController;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mNavigableController = (INavigable) activity;
		} catch (ClassCastException e) {
			throw new IllegalAccessError("BaseNavigableFragment must be attached to an INavigable activity.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mNavigableController = null;
	}

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode) {navigateTo(frag, mode, null);}

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params) {navigateTo(frag, mode, params, null, null);}

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params, AnimationType enterAnim, AnimationType exitAnim) {
		if (mNavigableController == null) {
			throw new IllegalStateException("The BaseNavigableFragment must be attached before navigate");
		}
		mNavigableController.navigateTo(frag, mode, params, enterAnim, exitAnim);
	}

	@Override public void navigateUp() {
		if (mNavigableController == null) {
			throw new IllegalStateException("The BaseNavigableFragment must be attached before navigate");
		}
		mNavigableController.navigateUp();
	}
}
