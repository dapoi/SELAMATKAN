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
import com.dafdev.selamatkan.data.domain.model.Province
import com.dafdev.selamatkan.databinding.FragmentProvinceBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceInsideAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceInsideViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvinceFragment : Fragment() {

    private var _binding: FragmentProvinceBinding? = null
    private val binding get() = _binding!!

    private val provinceViewModel: ProvinceInsideViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceInsideAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProvinceBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HelpUtil.setStatusBarWhite(requireActivity(), R.color.white)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (activity != null) {
            provinceAdapter = ProvinceInsideAdapter()
            provinceAdapter.setOnItemClick(object : ProvinceInsideAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Province) {
                    Constant.provinceId = data.id!!
                    findNavController().navigate(R.id.action_provinceFragment_to_cityFragment)
                }
            })

            binding.rvProvince.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = provinceAdapter
            }

            provinceViewModel.getListProvInside().observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        is Resource.Loading -> progressBar.showProgressBar(true)
                        is Resource.Success -> {
                            progressBar.showProgressBar(false)
                            provinceAdapter.setProvinceAdapter(it.data!!)
                        }
                        is Resource.Error -> {
                            progressBar.showProgressBar(false)
                            Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                        }
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