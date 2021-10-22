package com.dafdev.selamatkan.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.network.ApiConfig
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.HospitalNonCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalNonCovidViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status

class NonCovidHospitalFragment : Fragment() {

    private lateinit var binding: FragmentNonCovidHospitalBinding
    private lateinit var hospitalAdapter: HospitalNonCovidAdapter
    private lateinit var hospitalViewModel: HospitalNonCovidViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNonCovidHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        hospitalAdapter = HospitalNonCovidAdapter()
        with(binding.rvHospitalNonCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiService()))
        hospitalViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[HospitalNonCovidViewModel::class.java]
        hospitalViewModel.nonCovidHospital(Constant.provinsiId, Constant.kotaId)
            .observe(viewLifecycleOwner, {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> progressBar(true)
                        Status.SUCCESS -> {
                            progressBar(false)
                            hospitalAdapter.setNonCovidHospital(resource.data!!)
                        }
                        Status.ERROR -> {
                            progressBar(false)
                            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}