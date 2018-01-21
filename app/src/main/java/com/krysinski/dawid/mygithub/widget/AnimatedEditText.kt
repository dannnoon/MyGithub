package com.krysinski.dawid.mygithub.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.jakewharton.rxbinding2.view.focusChanges
import com.krysinski.dawid.mygithub.R

class AnimatedEditText : EditText {

    companion object {

        private const val FOCUSED_Z_TRANSLATION = 15f
        private const val NOT_FOCUSED_Z_TRANSLATION = 0f
        private const val ANIM_DURATION = 300L
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initFocusAnimator()

        setBackgroundResource(R.drawable.input_background)
    }

    @SuppressLint("CheckResult")
    private fun initFocusAnimator() {
        focusChanges().subscribe {
            if (it) {
                animate()
                        .translationZ(FOCUSED_Z_TRANSLATION)
                        .setDuration(ANIM_DURATION)
                        .start()
            } else {
                animate()
                        .translationZ(NOT_FOCUSED_Z_TRANSLATION)
                        .setDuration(ANIM_DURATION)
                        .start()
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled && isFocused) {
            translationZ = FOCUSED_Z_TRANSLATION
        } else if (!enabled && isFocused) {
            translationZ = NOT_FOCUSED_Z_TRANSLATION
        }

        super.setEnabled(enabled)
    }
}