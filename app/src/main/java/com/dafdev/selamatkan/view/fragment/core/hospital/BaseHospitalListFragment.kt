package com.dafdev.selamatkan.view.fragment.core.hospital

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentBaseHospitalListBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.view.adapter.pager.HospitalPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseHospitalListFragment : Fragment() {

    private lateinit var binding: FragmentBaseHospitalListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseHospitalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.white, binding.root)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            if (Constant.cityName == "Bekasi") {
                tvTitleHosptial.text = "Pilih Rumah Sakit Di Kab. Bekasi"
            } else {
                tvTitleHosptial.text = "Pilih Rumah Sakit Di ${Constant.cityName}"
            }

            val pagerAdapter = HospitalPagerAdapter(activity as AppCompatActivity)
            binding.viewPager.apply {
                adapter = pagerAdapter
                (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                TabLayoutMediator(tabsHospital, this) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.covid, R.string.non_covid)
    }
}