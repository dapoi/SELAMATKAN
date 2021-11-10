package com.dafdev.selamatkan.view.activity.onboard

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivityOnboardingBinding
import com.dafdev.selamatkan.utils.SharedPref
import com.dafdev.selamatkan.view.activity.main.HomeActivity
import com.dafdev.selamatkan.view.adapter.pager.OnboardingPagerAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPager()

        binding.btnSkip.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
            SharedPref.getInstance(this).setIsFirstLaunch()
            finish()
        }
    }

    private fun setUpPager() {
        binding.apply {
            val pagerOnboard = OnboardingPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerOnboard

            tabsIndicator.setupWithViewPager(viewPager, true)
        }
    }

}