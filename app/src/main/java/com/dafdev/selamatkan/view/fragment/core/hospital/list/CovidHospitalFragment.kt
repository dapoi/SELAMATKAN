package com.dafdev.selamatkan.view.fragment.core.hospital.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.hospital.list.HospitalCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidHospitalFragment : Fragment() {

    private lateinit var hospitalCovidAdapter: HospitalCovidAdapter
    private lateinit var binding: FragmentCovidHospitalBinding

    private val hospitalViewModel: HospitalCovidViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCovidHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
        swipeData()
    }

    private fun swipeData() {
        binding.apply {
            with(srlCovidHospital) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(requireActivity())
                        if (check) {
                            Handler(android.os.Looper.getMainLooper()).postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            Handler(android.os.Looper.getMainLooper()).postDelayed({
                                HelpUtil.noInternetView(
                                    false,
                                    viewNoConnected,
                                    rvHospitalCovid
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
        hospitalCovidAdapter = HospitalCovidAdapter()
        hospitalCovidAdapter.onItemClick = {
            Constant.hospitalId = it.id!!
            Constant.hospitalAddress = it.address!!
            Constant.hospitalName = it.name!!
            if (it.phone != null) {
                Constant.phoneNumber = it.phone
            } else {
                Constant.phoneNumber = "-"
            }
            findNavController().navigate(R.id.action_baseHospitalListFragment_to_baseHospitalDetailFragment)
        }
        with(binding.rvHospitalCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalCovidAdapter
        }
    }

    private fun setViewModel() {
        hospitalViewModel.covidHospital(
            Constant.provinceId,
            Constant.cityId
        ).observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> {
                        progressBar.showProgressBar(true)
                        viewEmpty.dataEmpty(false)
                        rvHospitalCovid.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        hospitalCovidAdapter.setCovidHospital(it.data!!)
                        rvHospitalCovid.visibility = View.VISIBLE
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
                    rvHospitalCovid
                )
            } else {
                viewEmpty.dataEmpty(false)
                HelpUtil.noInternetView(
                    true,
                    viewNoConnected,
                    rvHospitalCovid
                )
            }
        }
    }
}