@file:Suppress("DEPRECATION")

package com.dafdev.selamatkan.view.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dafdev.selamatkan.view.fragment.onboard.FirstOnboardingFragment
import com.dafdev.selamatkan.view.fragment.onboard.SecondOnboardingFragment
import com.dafdev.selamatkan.view.fragment.onboard.ThirdOnboardingFragment

class OnboardingPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FirstOnboardingFragment()
            1 -> SecondOnboardingFragment()
            2 -> ThirdOnboardingFragment()
            else -> FirstOnboardingFragment()
        }

}