package com.dafdev.selamatkan.view.adapter.pager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dafdev.selamatkan.view.fragment.core.hospital.detail.CovidHospitalDetailFragment
import com.dafdev.selamatkan.view.fragment.core.hospital.detail.NonCovidHospitalDetailFragment

class HospitalDetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> CovidHospitalDetailFragment()
            1 -> NonCovidHospitalDetailFragment()
            else -> Fragment()
        }
}