package com.dafdev.selamatkan.view.fragment.core.hospital.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.hospital.list.HospitalCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidHospitalFragment : Fragment() {

    private var _binding: FragmentCovidHospitalBinding? = null
    private val binding get() = _binding!!

    private val hospitalViewModel: HospitalCovidViewModel by viewModels()

    private lateinit var hospitalCovidAdapter: HospitalCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCovidHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        hospitalCovidAdapter = HospitalCovidAdapter()
        hospitalCovidAdapter.onItemCLick = {
            Constant.hospitalId = it.id!!
            Constant.hospitalAddress = it.address!!
            Constant.hospitalName = it.name!!
            if (it.phone != null) {
                Constant.phoneNumber = it.phone
            } else {
                Constant.phoneNumber = "-"
            }
            findNavController().navigate(R.id.action_baseHospitalListFragment_to_baseHospitalDetailFragment)
        }
        with(binding.rvHospitalCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalCovidAdapter
        }
    }

    private fun setViewModel() {
        hospitalViewModel.covidHospital(Constant.provinceId, Constant.cityId)
            .observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        is Resource.Loading -> progressBar.showProgressBar(true)
                        is Resource.Success -> {
                            progressBar.showProgressBar(false)
                            hospitalCovidAdapter.setCovidHospital(it.data!!)
                        }
                        is Resource.Error -> {
                            progressBar.showProgressBar(false)
                            viewEmpty.dataEmpty()
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