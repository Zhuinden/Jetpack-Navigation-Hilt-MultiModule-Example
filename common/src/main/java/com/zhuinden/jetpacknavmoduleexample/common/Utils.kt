package com.zhuinden.jetpacknavmoduleexample.common

import android.animation.Animator
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.ViewTreeObserver
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator


tailrec fun <T : Activity> Context.findActivity(): T {
    if (this is Activity) {
        @Suppress("UNCHECKED_CAST")
        return this as T
    } else {
        if (this is ContextWrapper) {
            return this.baseContext.findActivity()
        }
        throw IllegalStateException("The context does not contain Activity in the context chain!")
    }
}

fun View.objectAnimate() = ViewPropertyObjectAnimator.animate(this)

private typealias OnMeasuredCallback = (view: View, width: Int, height: Int) -> Unit

inline fun View.waitForMeasure(crossinline callback: OnMeasuredCallback) {
    if(isInEditMode) {
        return
    }

    val view = this
    val width = view.getWidth()
    val height = view.getHeight()

    if (width > 0 && height > 0) {
        callback(view, width, height)
        return
    }

    view.getViewTreeObserver().addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            val observer = view.getViewTreeObserver()
            if (observer.isAlive()) {
                observer.removeOnPreDrawListener(this)
            }

            callback(view, view.getWidth(), view.getHeight())

            return true
        }
    })
}

fun animateTogether(vararg animators: Animator) = AnimatorSet().apply {
    playTogether(*animators)
}


inline fun createAnimatorListener(
    crossinline onAnimationEnd: (Animator) -> Unit = {},
    crossinline onAnimationStart: (Animator) -> Unit = {},
    crossinline onAnimationCancel: (Animator) -> Unit = {},
    crossinline onAnimationRepeat: (Animator) -> Unit = {}
) = object : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator) = onAnimationRepeat(animation)

    override fun onAnimationEnd(animation: Animator) = onAnimationEnd(animation)

    override fun onAnimationCancel(animation: Animator) = onAnimationCancel(animation)

    override fun onAnimationStart(animation: Animator) = onAnimationStart(animation)
}

inline fun AnimatorSet.onAnimationEnd(crossinline onAnimationEnd: (Animator) -> Unit): AnimatorSet = apply {
    addListener(createAnimatorListener(onAnimationEnd = onAnimationEnd))
}

fun View.animateFadeOut(duration: Long = 325): Animator = run {
    alpha = 1f
    objectAnimate()
        .alpha(0f)
        .setDuration(duration)
        .get()
}

fun View.animateFadeIn(duration: Long = 325): Animator = run {
    alpha = 0f
    objectAnimate()
        .alpha(1f)
        .setDuration(duration)
        .get()
}

private fun View.animateTranslateXBy(from: Int, by: Int, duration: Long = 325): Animator = run {
    translationX = from.toFloat()
    objectAnimate()
        .translationXBy(by.toFloat())
        .setDuration(duration)
        .get()
}

fun View.animateTranslateIn(width: Int, direction: Int, duration: Long = 325): Animator =
    animateTranslateXBy(
        from = direction * width,
        by = (-1) * direction * width,
        duration = duration
    )

fun View.animateTranslateOut(width: Int, direction: Int, duration: Long = 325): Animator =
    animateTranslateXBy(
        from = 0,
        by = (-1) * direction * width,
        duration = duration
    )

inline fun <T: View> T.showIf(predicate: T.() -> Boolean) : T = this.apply {
    if(predicate(this)) {
        show()
    } else {
        hide()
    }
    return this
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.onClick(clickListener: (View) -> Unit) {
    setOnClickListener(clickListener)
}