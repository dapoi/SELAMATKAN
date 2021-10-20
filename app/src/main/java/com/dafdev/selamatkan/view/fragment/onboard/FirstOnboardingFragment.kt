package com.dafdev.selamatkan.view.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dafdev.selamatkan.databinding.FragmentFirstOnboardingBinding

class FirstOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentFirstOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }
}