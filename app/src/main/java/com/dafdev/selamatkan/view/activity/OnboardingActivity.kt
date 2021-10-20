package com.dafdev.selamatkan.view.activity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivityOnboardingBinding
import com.dafdev.selamatkan.view.adapter.OnboardingPagerAdapter
import com.google.firebase.auth.FirebaseAuth

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        setUpPager()

        with(binding) {
            btnSignUp.setOnClickListener {
                startActivity(Intent(this@OnboardingActivity, SignUpActivity::class.java))
            }

            btnLogin.setOnClickListener {
                startActivity(Intent(this@OnboardingActivity, LogInActivity::class.java))
            }
        }
    }

    private fun setUpPager() {
        binding.apply {
            val pagerOnboard = OnboardingPagerAdapter(supportFragmentManager)
            viewPager.adapter = pagerOnboard

            tabsIndicator.setupWithViewPager(viewPager, true)
        }
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser != null) {
            Intent(this, HomeActivity::class.java).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}