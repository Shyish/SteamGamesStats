package com.zdvdev.steamgamesstats.presentation.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout

import com.zdvdev.steamgamesstats.R

class RemovableEditText : RelativeLayout {

    private lateinit var editText: EditText
    private lateinit var deleteButton: ImageButton
    private var listener: OnFieldRemovedListener? = null

    interface OnFieldRemovedListener {
        fun onFieldRemoved(field: RemovableEditText)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        deleteButton = ImageButton(context)
        deleteButton.id = R.id.delete_button
        deleteButton.setImageResource(android.R.drawable.ic_delete)
        deleteButton.setOnClickListener {
            listener?.onFieldRemoved(this) ?: (parent as ViewGroup).removeView(this)
        }

        var lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addView(deleteButton, lp)

        editText = EditText(context)
        lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.addRule(RelativeLayout.LEFT_OF, R.id.delete_button)
        addView(editText, lp)
    }

    fun setOnFieldRemovedListener(listener: OnFieldRemovedListener) {
        this.listener = listener
    }

    fun setError(error: String) {
        editText.error = error
    }

    val text: String
        get() = editText.text.toString()

    fun setHint(hint: Int) {
        editText.setHint(hint)
    }

    fun setRemovable(removable: Boolean) {
        deleteButton.visibility = if (removable) View.VISIBLE else View.GONE
    }
}
