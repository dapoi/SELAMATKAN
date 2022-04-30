package com.dafdev.selamatkan.view.fragment.core.area

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.Cities
import com.dafdev.selamatkan.databinding.FragmentCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.noInternetView
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.CityAdapter
import com.dafdev.selamatkan.viewmodel.CitiesViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

    private var _binding: FragmentCityBinding? = null
    private val binding get() = _binding!!

    private val cityViewModel: CitiesViewModel by viewModels()

    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.white, binding.root)

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if (activity != null) {
            cityAdapter = CityAdapter()
            cityAdapter.setOnItemClick(object : CityAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Cities) {
                    Constant.cityId = data.id!!
                    findNavController().navigate(R.id.action_cityFragment_to_baseHospitalListFragment)
                }
            })
            binding.rvCity.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = cityAdapter
            }

            setViewModel()
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
                            Handler(Looper.getMainLooper()).postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            Handler(Looper.getMainLooper()).postDelayed({
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}