package com.zdvdev.steamgamesstats.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import static com.zdvdev.steamgamesstats.common.AnimationUtils.AnimationType;


/**
 * Created by jabellan on 27/05/14.
 */
public interface INavigable {

	public enum Mode {
		REPLACE, ADD
	}

	public void navigateTo(Class<? extends Fragment> frag, Mode mode);
	public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params);
	public void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params, AnimationType enterAnim, AnimationType exitAnim);
	public void navigateUp();
}
