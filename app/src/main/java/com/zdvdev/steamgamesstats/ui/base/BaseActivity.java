package com.zdvdev.steamgamesstats.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by jabellan on 27/05/14.
 */
public abstract class BaseActivity extends ActionBarActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/HelveticaNeue.ttf");
	}

	@Override protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(new CalligraphyContextWrapper(newBase));
	}
}
