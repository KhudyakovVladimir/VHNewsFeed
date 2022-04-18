package com.khudyakovvladimir.vhnewsfeed.utils

import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.google.android.material.animation.AnimationUtils
import com.khudyakovvladimir.vhnewsfeed.R

class AnimationHelper {

    fun fadeInView(img: View, duration: Long) {
        val fadeIn: Animation = AlphaAnimation(0F, 1F)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = duration
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeIn)
        img.visibility = View.VISIBLE
    }

    fun alpha(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.alpha)
        view.startAnimation(animation)
    }

    fun rotate(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.rotate)
        view.startAnimation(animation)
    }

    fun scale(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_two)
        view.startAnimation(animation)
    }

    fun translate(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.transform)
        view.startAnimation(animation)
    }

    fun rightToLeft(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.right_to_left)
        view.startAnimation(animation)
    }

    fun leftToRight(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.left_to_right)
        view.startAnimation(animation)
    }
}