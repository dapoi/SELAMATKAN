package com.dafdev.selamatkan.view.fragment.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeActivity)
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
        }, 3000)

        return binding.root
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoard", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("finish", false)
    }
}