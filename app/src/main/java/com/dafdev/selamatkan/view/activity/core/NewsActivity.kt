package com.dafdev.selamatkan.view.activity.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.view.fragment.core.news.TopNewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val fManager = supportFragmentManager
        val topNewsFragment = TopNewsFragment()
        val fragment = fManager.findFragmentByTag(TopNewsFragment::class.java.simpleName)

        if (fragment !is TopNewsFragment) {
            fManager.beginTransaction()
                .add(R.id.fl_container, topNewsFragment, TopNewsFragment::class.java.simpleName)
                .commit()
        }
    }
}