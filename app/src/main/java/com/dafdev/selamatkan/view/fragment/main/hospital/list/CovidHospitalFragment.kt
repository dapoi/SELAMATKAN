package com.dafdev.selamatkan.view.fragment.main.hospital.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiConfig
import com.dafdev.selamatkan.databinding.FragmentCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.hospital.list.HospitalCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalCovidViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status

class CovidHospitalFragment : Fragment() {

    private lateinit var binding: FragmentCovidHospitalBinding
    private lateinit var hospitalCovidAdapter: HospitalCovidAdapter
    private lateinit var hospitalViewModel: HospitalCovidViewModel

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
    }

    private fun setAdapter() {
        hospitalCovidAdapter = HospitalCovidAdapter(requireActivity())
        with(binding.rvHospitalCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalCovidAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiHospital()))
        hospitalViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[HospitalCovidViewModel::class.java]
        hospitalViewModel.covidHospital(Constant.provinsiId, Constant.kotaId)
            .observe(viewLifecycleOwner, {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> progressBar(true)
                        Status.SUCCESS -> {
                            progressBar(false)
                            if (resource.data.isNullOrEmpty()) {
                                dataEmpty()
                            } else {
                                hospitalCovidAdapter.setCovidHospital(resource.data)
                            }
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

    private fun dataEmpty() {
        binding.viewEmpty.root.visibility = View.VISIBLE
    }
}