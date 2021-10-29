package com.dafdev.selamatkan.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(SESSION, MODE)

    fun saveUser(email: String, name: String) {
        val editor = sharedPref.edit()
        with(editor) {
            putString(SESSION_EMAIL, email)
            putString(SESSION_NAME, name)
            putBoolean(SESSION_ISLOGIN, true)
            apply()
        }
    }

    fun getUser(): User {
        val email = sharedPref.getString(SESSION_EMAIL, "")
        val name = sharedPref.getString(SESSION_NAME, "")
        return User(email, name)
    }

    @SuppressLint("CommitPrefEdits")
    fun clearUser() {
        val editor = sharedPref.edit()
        editor.clear()
    }

    companion object {
        const val SESSION = "SESSION"
        const val MODE = Context.MODE_PRIVATE

        const val SESSION_EMAIL = "email"
        const val SESSION_NAME = "name"
        const val SESSION_ISLOGIN = "isLogin"
    }
}