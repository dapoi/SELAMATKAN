package com.dafdev.selamatkan.view.fragment.main.hospital.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.hospital.list.HospitalNonCovidAdapter
import com.dafdev.selamatkan.viewmodel.HospitalNonCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NonCovidHospitalFragment : Fragment() {

    private var _binding: FragmentNonCovidHospitalBinding? = null
    private val binding get() = _binding!!

    private val hospitalViewModel: HospitalNonCovidViewModel by viewModels()

    private lateinit var hospitalAdapter: HospitalNonCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNonCovidHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        hospitalAdapter = HospitalNonCovidAdapter()
        hospitalAdapter.onItemClick = {
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
        with(binding.rvHospitalNonCovid) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = hospitalAdapter
        }
    }

    private fun setViewModel() {
        hospitalViewModel.nonCovidHospital(Constant.provinceId, Constant.cityId)
            .observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        is Resource.Loading -> progressBar.showProgressBar(true)
                        is Resource.Success -> {
                            progressBar.showProgressBar(false)
                            hospitalAdapter.setNonCovidHospital(it.data!!)
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