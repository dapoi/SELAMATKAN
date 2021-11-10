package com.dafdev.selamatkan.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPref private constructor() {

    @SuppressLint("CommitPrefEdits")
    fun setIsFirstLaunch() {
        with(editor!!) {
            putBoolean("firsr", false)
            apply()
        }
    }

    fun isFirstLaunch(): Boolean = sharedPreference!!.getBoolean("first", true)

    companion object {
        private val sharedPref = SharedPref()
        private var sharedPreference: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        @Synchronized
        fun getInstance(context: Context): SharedPref {

            if (sharedPreference == null) {
                sharedPreference =
                    context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
                editor = sharedPreference!!.edit()
            }

            return sharedPref
        }
    }
}