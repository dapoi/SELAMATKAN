package com.dafdev.selamatkan.view.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentFirstOnboardingBinding

class FirstOnboardingFragment : Fragment() {

    private lateinit var binding: FragmentFirstOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstOnboardingBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager2)
        binding.next1.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }
}