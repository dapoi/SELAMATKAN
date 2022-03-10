package com.dafdev.selamatkan.view.fragment.main.hospital

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentBaseHospitalListBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.view.adapter.pager.HospitalPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseHospitalListFragment : Fragment() {

    private var _binding: FragmentBaseHospitalListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseHospitalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HelpUtil.setStatusBarWhite(requireActivity(), R.color.white)

        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            val pagerAdapter = HospitalPagerAdapter(activity as AppCompatActivity)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(tabsHospital, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.covid, R.string.non_covid)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}