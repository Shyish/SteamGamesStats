package com.zdvdev.steamgamesstats.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 05/06/14
 */
public abstract class BaseFragment extends Fragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ButterKnife.inject(this, view);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.reset(this);
	}
}
