package com.dafdev.selamatkan.view.fragment.core.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.FragmentMainBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.setStatusBarColor
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val provinceViewModel: ProvinceViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.blue)

        binding.etSearch.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    return true
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
                findNavController().navigate(R.id.action_nav_main_to_cityFragment)
            }
        })

        binding.rvProvince.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = provinceAdapter
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        provinceAdapter.filter.filter(binding.etSearch.query)
    }
}