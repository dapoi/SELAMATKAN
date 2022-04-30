package com.dafdev.selamatkan.view.fragment.core.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.FragmentHomeBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.hideKeyboard
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val provinceViewModel: ProvinceViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.blue, binding.root, false)

        binding.etSearch.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard(requireActivity())
                    clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    provinceAdapter.filter.filter(newText)
                    return false
                }
            })
        }

        Calendar.getInstance().also {
            with(binding.tvWeather) {
                when (it.get(Calendar.HOUR_OF_DAY)) {
                    in 0..11 -> text = "Selamat Pagi"
                    in 12..15 -> text = "Selamat Siang"
                    in 16..18 -> text = "Selamat Sore"
                    in 19..24 -> text = "Selamat Malam"
                }
            }
        }

        setAdapter()
        setViewModel()
    }

    private fun setViewModel() {
        provinceViewModel.getListProv.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        setDataToAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter(data: List<ProvinceEntity>) {
        provinceAdapter.setProvinceAdapter(data)
        provinceAdapter.notifyDataSetChanged()
    }

    private fun setAdapter() {
        provinceAdapter = ProvinceAdapter()
        provinceAdapter.setOnItemClick(object : ProvinceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProvinceEntity) {
                Constant.provinceId = data.id
                findNavController().navigate(R.id.action_nav_home_to_mainActivity)
            }
        })

        binding.rvProvince.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = provinceAdapter
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        provinceAdapter.filter.filter(binding.etSearch.query)
        binding.etSearch.clearFocus()
        super.onResume()
    }
}