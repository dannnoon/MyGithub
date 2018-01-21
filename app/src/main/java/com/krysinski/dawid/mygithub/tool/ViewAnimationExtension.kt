package com.krysinski.dawid.mygithub.tool

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

const val DEFAULT_ANIM_DURATION = 300L

fun View.transformTo(
        view: View,
        actionAfter: (() -> Unit)? = null,
        duration: Long = DEFAULT_ANIM_DURATION
) {
    animate()
            .setDuration(duration)
            .scaleX(0.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.GONE
                    scaleX = 1.0f

                    view.scaleX = 0.0f
                    view.visibility = View.VISIBLE
                    view
                            .animate()
                            .setDuration(duration)
                            .scaleX(1.0f)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    actionAfter?.invoke()
                                }
                            })
                            .start()
                }
            })
            .start()
}