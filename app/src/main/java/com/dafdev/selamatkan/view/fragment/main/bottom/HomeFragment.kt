package com.dafdev.selamatkan.view.fragment.main.bottom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.FragmentHomeBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.IndoDataCovidViewModel
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val provinceViewModel: ProvinceViewModel by viewModels()
    private val covidViewModel: IndoDataCovidViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.blue)

        binding.apply {
            Calendar.getInstance().also {
                with(tvWeather) {
                    when (it.get(Calendar.HOUR_OF_DAY)) {
                        in 0..11 -> text = "Selamat Pagi"
                        in 12..15 -> text = "Selamat Siang"
                        in 16..18 -> text = "Selamat Sore"
                        in 19..24 -> text = "Selamat Malam"
                    }
                }
            }

            tvViewAll.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_provinceCovidFragment)
            }

            tvViewClear.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_provinceFragment)
            }
        }

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        provinceAdapter = ProvinceAdapter()
        provinceAdapter.setOnItemClick(object : ProvinceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProvinceEntity) {
                Constant.provinceId = data.id
                findNavController().navigate(R.id.action_nav_home_to_cityFragment)
            }
        })
        binding.rvProvince.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = provinceAdapter
        }
    }

    private fun setViewModel() {
        covidViewModel.dataCovidIndo.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> loadingCovid(true)
                is Resource.Success -> {
                    loadingCovid(false)
                    binding.apply {
                        tvPositive.text = it.data?.positif?.toLong().toString()
                        tvNegative.text = it.data?.sembuh?.toLong().toString()
                        tvDeath.text = it.data?.meninggal?.toLong().toString()
                    }
                }
                is Resource.Error -> {
                    loadingCovid(false)
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        provinceViewModel.getListProv.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        provinceAdapter.setProvinceAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loadingCovid(state: Boolean) {
        binding.apply {
            if (state) {
                cardCovid.visibility = View.GONE
                loadingCovid.visibility = View.VISIBLE
            } else {
                cardCovid.visibility = View.VISIBLE
                loadingCovid.visibility = View.GONE
            }
        }
    }
}