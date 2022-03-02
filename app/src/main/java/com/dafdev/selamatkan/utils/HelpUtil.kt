package com.dafdev.selamatkan.utils

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat

object StatusBarColor {
    fun setStatusBar(activity: Activity, color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }

    fun ProgressBar.showProgressBar(state: Boolean) {
        visibility = if (state) View.VISIBLE else View.GONE
    }
}