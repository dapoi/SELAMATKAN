package com.dafdev.selamatkan.view.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentSecondOnboardingBinding

class SecondOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentSecondOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondOnboardingBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager2)
        binding.next2.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return binding.root
    }

}