package com.dafdev.selamatkan.utils

import android.app.Activity
import androidx.core.content.ContextCompat

object StatusBarColor {
    fun setStatusBar(activity: Activity, color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }
}