package com.zdvdev.steamgamesstats.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import static com.zdvdev.steamgamesstats.common.AnimationUtils.AnimationType;

public interface Navigable {

    enum Mode {
        REPLACE,
        ADD,
        OVERLAP
    }

    void navigateTo(Class<? extends Fragment> frag, Mode mode);

    void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params);

    void navigateTo(Class<? extends Fragment> frag, Mode mode, Bundle params, AnimationType enterAnim,
          AnimationType exitAnim);

    void navigateUp();
}
