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
import androidx.viewpager2.widget.ViewPager2
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentBaseHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.view.adapter.pager.HospitalDetailPagerAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseHospitalDetailFragment : Fragment() {

    private var _binding: FragmentBaseHospitalDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.white, binding.root)

        with(binding) {

            fab.setOnClickListener {
                findNavController().navigate(R.id.action_baseHospitalDetailFragment_to_mapActivity)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            tvTitleHosptial.text = "Kapasitas ${Constant.hospitalName}"

            val pagerAdapter = HospitalDetailPagerAdapter(activity as AppCompatActivity)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(tabsHospital, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_SETTLING -> fab.hide()
                        else -> fab.show()
                    }
                }
            })
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.khusus, R.string.umum)
        fun RecyclerView.attachFab(fab: ExtendedFloatingActionButton) {
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 20 && fab.isExtended) {
                        fab.shrink()
                    }

                    if (dy < -10 && !fab.isExtended) {
                        fab.extend()
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}