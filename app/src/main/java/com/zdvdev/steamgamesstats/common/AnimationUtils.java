package com.zdvdev.steamgamesstats.common;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import com.zdvdev.steamgamesstats.R;

/**
 * - * Created with Android Studio.
 * Very simple class to handle basic translate visual (so there's no real movement) animations
 *
 * @author aballano
 *         Date: 28/04/14
 */
public class AnimationUtils {

	public static enum AnimationType {
		SLIDE_IN_TOP(R.anim.slide_in_from_top),
		SLIDE_IN_BOTTOM(R.anim.slide_in_from_bottom),
		SLIDE_OUT_TOP(R.anim.slide_out_to_top),
		SLIDE_OUT_BOTTOM(R.anim.slide_out_to_bottom),

		SLIDE_IN_LEFT(R.anim.slide_in_from_left),
		SLIDE_IN_RIGHT(R.anim.slide_in_from_right),
		SLIDE_OUT_LEFT(R.anim.slide_out_to_left),
		SLIDE_OUT_RIGHT(R.anim.slide_out_to_right);

		public final int mAnimId;

		AnimationType(int animId) {
			mAnimId = animId;
		}
	}

	/**
	 * Method to simulate a movement of the view. NOTE: This method DOESN'T move the view, it simply animates it, so you should position it first.
	 *
	 * @param v
	 * 		The view to animate
	 * @param type
	 * 		The animation type
	 */
	public static void animateView(View v, AnimationType type) {
		boolean isOut = (type == AnimationType.SLIDE_OUT_TOP || type == AnimationType.SLIDE_OUT_BOTTOM);
		if (v != null && isOut ? v.getVisibility() == View.VISIBLE : v.getVisibility() != View.VISIBLE) {
			if (!isOut) {
				v.setVisibility(View.VISIBLE);
			}
			Animation a = android.view.animation.AnimationUtils.loadAnimation(v.getContext(), type.mAnimId);
			v.clearAnimation();
			v.startAnimation(a);
			if (isOut) {
				v.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * @param act
	 * 		Activity which starts another activity or is going to finish
	 * @param enterAnim
	 * 		New activity's enter animation
	 * @param exitAnim
	 * 		Current activity's exit animation
	 * 		Simple method to animate an activity after performing a transaction. NOTE: this method must be called just after startActivity() or finish()
	 * @param act
	 * 		Activity which starts another activity or is going to finish
	 * @param enterAnim
	 * 		New activity's enter animation
	 * @param exitAnim
	 * 		Current activity's exit animation
	 */
	public static void animateActivity(Activity act, AnimationType enterAnim, AnimationType exitAnim) {
		act.overridePendingTransition(enterAnim.mAnimId, exitAnim.mAnimId);
	}


}
