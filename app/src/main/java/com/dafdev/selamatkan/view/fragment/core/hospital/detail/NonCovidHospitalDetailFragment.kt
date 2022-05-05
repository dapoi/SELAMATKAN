package com.dafdev.selamatkan.view.fragment.core.hospital.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.hospital.detail.HospitalDetailNonCovidAdapter
import com.dafdev.selamatkan.view.fragment.core.hospital.BaseHospitalDetailFragment.Companion.attachFab
import com.dafdev.selamatkan.viewmodel.DetailNonCovidHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NonCovidHospitalDetailFragment : Fragment() {

    private var _binding: FragmentNonCovidHospitalDetailBinding? = null
    private val binding get() = _binding!!

    private val nonCovidDetailViewModel: DetailNonCovidHospitalViewModel by viewModels()

    private lateinit var nonCovidDetailAdapter: HospitalDetailNonCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNonCovidHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
        swipeData()

        val rv = binding.rvHospitalDetailNonCovid
        rv.attachFab(requireActivity().findViewById(R.id.fab))
    }

    private fun swipeData() {
        binding.apply {
            with(srlNonCovidHospitalDetail) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(requireActivity())
                        if (check) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            Handler(Looper.getMainLooper()).postDelayed({
                                HelpUtil.noInternetView(
                                    false,
                                    viewNoConnected,
                                    rvHospitalDetailNonCovid
                                )
                                setViewModel()
                            }, 2350)
                        } else {
                            stateEmptyData(false)
                            setRefreshing(false)
                        }
                    }
                })
            }
        }
    }

    private fun setAdapter() {
        nonCovidDetailAdapter = HospitalDetailNonCovidAdapter()
        binding.rvHospitalDetailNonCovid.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = nonCovidDetailAdapter
        }
    }

    private fun setViewModel() {
        nonCovidDetailViewModel.dataDetailNonCovidHospital(
            Constant.hospitalId
        ).observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> {
                        progressBar.showProgressBar(true)
                        viewEmpty.dataEmpty(false)
                        rvHospitalDetailNonCovid.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        nonCovidDetailAdapter.setData(it.data!!)
                        rvHospitalDetailNonCovid.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        val check = isOnline(requireActivity())
                        if (check) {
                            stateEmptyData(true)
                        } else {
                            stateEmptyData(false)
                        }
                    }
                }
            }
        }
    }

    private fun stateEmptyData(value: Boolean) {
        binding.apply {
            if (value) {
                viewEmpty.dataEmpty(true)
                HelpUtil.noInternetView(
                    false,
                    viewNoConnected,
                    rvHospitalDetailNonCovid
                )
            } else {
                viewEmpty.dataEmpty(false)
                HelpUtil.noInternetView(
                    true,
                    viewNoConnected,
                    rvHospitalDetailNonCovid
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}