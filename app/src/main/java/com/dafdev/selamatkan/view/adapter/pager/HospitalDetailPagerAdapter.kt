package com.dafdev.selamatkan.view.adapter.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.view.fragment.main.hospital.detail.CovidHospitalDetailFragment
import com.dafdev.selamatkan.view.fragment.main.hospital.detail.NonCovidHospitalDetailFragment

class HospitalDetailPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> CovidHospitalDetailFragment()
            1 -> NonCovidHospitalDetailFragment()
            else -> CovidHospitalDetailFragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLES[position])

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.covid, R.string.non_covid)
    }
}