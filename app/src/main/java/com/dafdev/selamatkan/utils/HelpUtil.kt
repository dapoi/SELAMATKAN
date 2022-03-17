package com.dafdev.selamatkan.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.databinding.EmptyLayoutBinding
import com.dafdev.selamatkan.databinding.NoInternetLayoutBinding
import com.facebook.shimmer.ShimmerFrameLayout

object HelpUtil {

    fun setStatusBarColor(activity: Activity, color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }

    fun ShimmerFrameLayout.showProgressBar(state: Boolean) {
        visibility = if (state) View.VISIBLE else View.GONE
    }

    fun EmptyLayoutBinding.dataEmpty() {
        root.visibility = View.VISIBLE
    }

    fun isOnline(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    fun noInternetView(state: Boolean, layout: NoInternetLayoutBinding, rv: RecyclerView) {
        if (state) {
            layout.root.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            layout.root.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
    }
}