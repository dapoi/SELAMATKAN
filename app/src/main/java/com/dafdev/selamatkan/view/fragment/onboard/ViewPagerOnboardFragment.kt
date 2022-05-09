package com.dafdev.selamatkan.view.fragment.onboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentViewPagerOnboardBinding
import com.dafdev.selamatkan.view.activity.core.HomeActivity
import com.dafdev.selamatkan.view.adapter.pager.OnboardingPagerAdapter

class ViewPagerOnboardFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerOnboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewPager2.adapter = context?.let { OnboardingPagerAdapter(requireActivity(), it) }
            viewPager2.offscreenPageLimit = 1
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 3) {
                        btnNext.text = getText(R.string.finish)
                    } else {
                        btnNext.text = getText(R.string.next)
                    }

                    if (position == 0) {
                        btnBack.text = getText(R.string.out)
                    } else {
                        btnBack.text = getText(R.string.back)
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            tabIndicator.setViewPager2(viewPager2)

            btnNext.setOnClickListener {
                if (getItem() > 2) {
                    Intent(requireContext(), HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    requireActivity().finish()
                    onBoardingFinished()
                } else {
                    viewPager2.setCurrentItem(getItem() + 1, true)
                }
            }

            btnBack.setOnClickListener {
                if (getItem() == 0) {
                    requireActivity().finish()
                } else {
                    viewPager2.setCurrentItem(getItem() - 1, true)
                }
            }
        }
    }

    private fun getItem(): Int = binding.viewPager2.currentItem

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoard", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        with(editor) {
            putBoolean("finish", true)
            apply()
        }
    }
}