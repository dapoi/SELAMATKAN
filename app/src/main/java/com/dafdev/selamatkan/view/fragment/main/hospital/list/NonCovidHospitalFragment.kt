package com.dafdev.selamatkan.view.fragment.main.hospital.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.hospital.list.HospitalNonCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalNonCovidViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar

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
        hospitalAdapter = HospitalNonCovidAdapter(requireActivity())
        with(binding.rvHospitalNonCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        hospitalViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[HospitalNonCovidViewModel::class.java]
        hospitalViewModel.nonCovidHospital(Constant.provinsiId, Constant.kotaId)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> progressBar(true)
                    is Resource.Success -> {
                        progressBar(false)
                        if (it.data.isNullOrEmpty()) {
                            dataEmpty()
                        } else {
                            hospitalAdapter.setNonCovidHospital(it.data)
                        }
                    }
                    is Resource.Error -> {
                        progressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
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