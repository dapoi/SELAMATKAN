package com.dafdev.selamatkan.view.fragment.core.area

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.Cities
import com.dafdev.selamatkan.databinding.FragmentCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.noInternetView
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.CityAdapter
import com.dafdev.selamatkan.viewmodel.CitiesViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var handlerData: Handler

    private val cityViewModel: CitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized) {
            binding
        } else {
            binding = FragmentCityBinding.inflate(inflater, container, false)
            setAdapter()
            setViewModel()
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlerData = Handler(Looper.getMainLooper())
        handlerData.postDelayed({
            HelpUtil.setStatusBarColor(requireActivity(), R.color.white, binding.root)
        }, 750)

        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            if (Constant.provinceName == "DI Yogyakarta") {
                tvTitleCity.text = "Pilih Daerah Di Yogyakarta"
            } else {
                tvTitleCity.text = "Pilih Daerah Di ${Constant.provinceName}"
            }

            svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    HelpUtil.hideKeyboard(requireActivity())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    cityAdapter.filter.filter(newText)
                    return true
                }
            })
        }

        swipeData()
    }

    private fun swipeData() {
        binding.apply {
            with(srlCity) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(requireActivity())
                        if (check) {
                            handlerData.postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            handlerData.postDelayed({
                                noInternetView(false, viewNoConnected, rvCity)
                                setViewModel()
                            }, 2350)
                        } else {
                            noInternetView(true, viewNoConnected, rvCity)
                            setRefreshing(false)
                        }
                    }
                })
            }
        }
    }

    private fun setAdapter() {
        cityAdapter = CityAdapter()
        cityAdapter.setOnItemClick(object : CityAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Cities) {
                Constant.cityId = data.id!!
                Constant.cityName = data.name!!
                findNavController().navigate(R.id.action_cityFragment_to_baseHospitalListFragment)
            }
        })

        binding.rvCity.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = cityAdapter
        }
    }

    private fun setViewModel() {
        cityViewModel.dataCity(Constant.provinceId).observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> {
                        progressBar.showProgressBar(true)
                        rvCity.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        cityAdapter.setCityAdapter(it.data!!)
                        rvCity.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        viewNoConnected.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}