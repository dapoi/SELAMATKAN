package com.dafdev.selamatkan.view.activity.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dafdev.selamatkan.databinding.ActivityResetBinding

class ResetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}