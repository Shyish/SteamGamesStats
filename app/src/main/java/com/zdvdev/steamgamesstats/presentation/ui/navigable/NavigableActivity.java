package com.zdvdev.steamgamesstats.presentation.ui.navigable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.zdvdev.steamgamesstats.common.Navigable;
import com.zdvdev.steamgamesstats.presentation.ui.base.BaseActivity;

import static com.zdvdev.steamgamesstats.common.AnimationUtils.AnimationType;

public abstract class NavigableActivity extends BaseActivity implements Navigable {

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode) {
		navigateTo(frag, mode, null);
	}

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params) {
		navigateTo(frag, mode, params, null, null);
	}

	@Override public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params, AnimationType enterAnim, AnimationType exitAnim) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = makeFragment(frag, params);

		if (enterAnim != null && exitAnim != null) {
			ft.setCustomAnimations(enterAnim.mAnimId, 0, 0, exitAnim.mAnimId);
		}

		switch (mode) {
			case REPLACE:
				ft.replace(getFrameNavigationContainer(), fragment, null);
				break;
			case ADD:
				Fragment previousFragment = getSupportFragmentManager().findFragmentById(getFrameNavigationContainer());
				if (previousFragment != null){
					ft.hide(previousFragment);
				}
			case OVERLAP:
				ft.add(getFrameNavigationContainer(), fragment, null);
				ft.addToBackStack(null);
				break;

		}
		ft.commit();
	}

	@Override public void navigateUp() {
		getSupportFragmentManager().popBackStack();
	}

	private static Fragment makeFragment(Class<? extends Fragment> claz, Bundle params) {
		try {
			Fragment fragment = claz.newInstance();
			fragment.setArguments(params);
			return fragment;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Override this to provide a valid FrameLayout identifier used to navigate
	 */
	protected abstract int getFrameNavigationContainer();
}
