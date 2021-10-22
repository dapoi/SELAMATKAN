package com.dafdev.selamatkan.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dafdev.selamatkan.view.fragment.main.CovidHospitalFragment
import com.dafdev.selamatkan.view.fragment.main.NonCovidHospitalFragment

class HospitalPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> CovidHospitalFragment()
            1 -> NonCovidHospitalFragment()
            else -> CovidHospitalFragment()
        }
}