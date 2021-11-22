package com.dafdev.selamatkan.view.activity.onboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}