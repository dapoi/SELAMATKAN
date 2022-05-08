package com.dafdev.selamatkan.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class InternetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status = HelpUtil.isOnline(context)
        if (status) {
            (context as Activity).runOnUiThread {
                recreateActivity(context)
            }
        }
    }

    private fun recreateActivity(activity: Activity) {
        activity.intent.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            activity.finish()
            activity.overridePendingTransition(0, 0)
            activity.startActivity(it)
            activity.overridePendingTransition(0, 0)
        }
    }
}