package com.dafdev.selamatkan.view.fragment.main.area

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentProvinceCovidBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceCovidAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvinceCovidFragment : Fragment() {

    private var _binding: FragmentProvinceCovidBinding? = null
    private val binding get() = _binding!!

    private val covidViewModel: ProvinceCovidViewModel by viewModels()

    private lateinit var covidAdapter: ProvinceCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProvinceCovidBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HelpUtil.setStatusBarWhite(requireActivity(), R.color.white)

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        covidAdapter = ProvinceCovidAdapter()
        binding.rvProvince.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = covidAdapter
        }
    }

    private fun setViewModel() {
        covidViewModel.dataCovidProv().observe(requireActivity()) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        covidAdapter.setData(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}