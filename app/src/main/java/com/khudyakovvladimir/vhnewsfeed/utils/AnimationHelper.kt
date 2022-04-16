package com.khudyakovvladimir.vhnewsfeed.utils

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

class AnimationHelper {

    fun fadeInView(img: View) {
        val fadeIn: Animation = AlphaAnimation(0F, 1F)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = 3000
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeIn)
    }
}