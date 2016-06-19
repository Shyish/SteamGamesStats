package com.zdvdev.steamgamesstats.presentation.ui;

import android.os.Bundle;

import com.zdvdev.steamgamesstats.R;
import com.zdvdev.steamgamesstats.presentation.ui.navigable.NavigableActivity;

public class MainActivity extends NavigableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateTo(UserSearchFragment.class, Mode.REPLACE);
        }
    }

    @Override
    protected int getFrameNavigationContainer() {
        return R.id.container;
    }
}
