package com.dafdev.selamatkan.view.fragment.main.area

import android.os.Bundle
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
import com.dafdev.selamatkan.utils.StatusBarColor
import com.dafdev.selamatkan.view.adapter.CityAdapter
import com.dafdev.selamatkan.viewmodel.CitiesViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
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

        StatusBarColor.setStatusBar(requireActivity(), R.color.white)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
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

            cityViewModel.dataCity(Constant.provinceId).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> progressBar(true)
                    is Resource.Success -> {
                        progressBar(false)
                        cityAdapter.setCityAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}