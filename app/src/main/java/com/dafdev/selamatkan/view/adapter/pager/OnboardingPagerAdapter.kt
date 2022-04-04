package com.dafdev.selamatkan.view.adapter.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.view.fragment.onboard.OnboardingFragment

class OnboardingPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment.newInstance(
                context.getString(R.string.title1),
                context.getString(R.string.desc1),
                R.raw.hospital
            )
            1 -> OnboardingFragment.newInstance(
                context.getString(R.string.title2),
                context.getString(R.string.desc2),
                R.raw.data
            )
            2 -> OnboardingFragment.newInstance(
                context.getString(R.string.title3),
                context.getString(R.string.desc3),
                R.raw.news
            )
            else -> OnboardingFragment.newInstance(
                context.getString(R.string.title4),
                context.getString(R.string.desc4),
                R.raw.bmi
            )
        }
    }
}