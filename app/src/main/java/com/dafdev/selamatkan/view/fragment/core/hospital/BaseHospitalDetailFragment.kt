package com.dafdev.selamatkan.view.fragment.core.hospital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentBaseHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.view.activity.core.MapActivity
import com.dafdev.selamatkan.view.adapter.pager.HospitalDetailPagerAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseHospitalDetailFragment : Fragment() {

    private lateinit var binding: FragmentBaseHospitalDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.white, binding.root)

        with(binding) {

            fab.setOnClickListener {
                startActivity(Intent(requireActivity(), MapActivity::class.java))
                requireActivity().overridePendingTransition(R.anim.from_right, R.anim.to_left)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            tvTitleHosptial.text = "Kapasitas ${Constant.hospitalName}"

            val pagerAdapter = HospitalDetailPagerAdapter(activity as AppCompatActivity)
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
}