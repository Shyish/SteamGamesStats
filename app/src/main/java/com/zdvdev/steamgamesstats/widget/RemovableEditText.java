package com.zdvdev.steamgamesstats.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 18/07/14
 */
public class RemovableEditText extends RelativeLayout implements View.OnClickListener {

	private EditText mEditText;
	private ImageButton mDeleteButton;

	public RemovableEditText(Context context) {
		super(context);
		init();
	}

	public RemovableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public RemovableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mDeleteButton = new ImageButton(getContext());
		mDeleteButton.setId(1234);
		mDeleteButton.setImageResource(android.R.drawable.ic_delete);
		mDeleteButton.setOnClickListener(this);

		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.addRule(ALIGN_PARENT_RIGHT);
		addView(mDeleteButton, lp);

		mEditText = new EditText(getContext());
		lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.addRule(LEFT_OF, 1234);
		addView(mEditText, lp);
	}

	@Override public void onClick(View v) {
		((ViewGroup) getParent()).removeView(this);
	}

	public void setError(String string) {
		mEditText.setError(string);
	}

	public String getText() {
		return mEditText.getText().toString();
	}

	public void setHint(int hint) {
		mEditText.setHint(hint);
	}

	public void setRemovable(boolean removable) {
		if (removable) {
			mDeleteButton.setVisibility(View.VISIBLE);
		} else {
			mDeleteButton.setVisibility(View.GONE);
		}
	}
}
