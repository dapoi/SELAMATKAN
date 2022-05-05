package com.dafdev.selamatkan.view.fragment.core.hospital.detail

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentCovidHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.hospital.detail.HospitalDetailCovidAdapter
import com.dafdev.selamatkan.view.fragment.core.hospital.BaseHospitalDetailFragment.Companion.attachFab
import com.dafdev.selamatkan.viewmodel.DetailCovidHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidHospitalDetailFragment : Fragment() {

    private var _binding: FragmentCovidHospitalDetailBinding? = null
    private val binding get() = _binding!!

    private val covidDetailViewModel: DetailCovidHospitalViewModel by viewModels()

    private lateinit var covidDetailAdapter: HospitalDetailCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCovidHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
        swipeData()

        val rv = binding.rvHospitalDetailCovid
        rv.attachFab(requireActivity().findViewById(R.id.fab))
    }

    private fun swipeData() {
        binding.apply {
            with(srlCovidHospitalDetail) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = HelpUtil.isOnline(requireActivity())
                        if (check) {
                            Handler(android.os.Looper.getMainLooper()).postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            Handler(android.os.Looper.getMainLooper()).postDelayed({
                                HelpUtil.noInternetView(
                                    false,
                                    viewNoConnected,
                                    rvHospitalDetailCovid
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
        covidDetailAdapter = HospitalDetailCovidAdapter()
        binding.rvHospitalDetailCovid.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = covidDetailAdapter
        }
    }

    private fun setViewModel() {
        covidDetailViewModel.dataDetailCovidHospital(
            Constant.hospitalId
        ).observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> {
                        progressBar.showProgressBar(true)
                        viewEmpty.dataEmpty(false)
                        rvHospitalDetailCovid.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        covidDetailAdapter.setData(it.data!!)
                        rvHospitalDetailCovid.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        val check = HelpUtil.isOnline(requireActivity())
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
                    rvHospitalDetailCovid
                )
            } else {
                viewEmpty.dataEmpty(false)
                HelpUtil.noInternetView(
                    true,
                    viewNoConnected,
                    rvHospitalDetailCovid
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}