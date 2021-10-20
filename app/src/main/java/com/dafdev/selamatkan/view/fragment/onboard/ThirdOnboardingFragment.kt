package com.dafdev.selamatkan.view.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dafdev.selamatkan.databinding.FragmentSecondOnboardingBinding
import com.dafdev.selamatkan.databinding.FragmentThirdOnboardingBinding

class ThirdOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentThirdOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }
}