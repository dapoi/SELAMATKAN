package com.dafdev.selamatkan.view.fragment.main.hospital.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.databinding.FragmentCovidHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.hospital.detail.HospitalDetailCovidAdapter
import com.dafdev.selamatkan.viewmodel.DetailCovidHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidHospitalDetailFragment : Fragment() {

    private lateinit var binding: FragmentCovidHospitalDetailBinding
    private lateinit var covidDetailAdapter: HospitalDetailCovidAdapter
    private val covidDetailViewModel: DetailCovidHospitalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCovidHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()

        binding.fab.setOnClickListener {
            if (Constant.phoneNumber == "") {
                Toast.makeText(
                    requireActivity(),
                    "Maaf, nomor rumah sakit ini tidak diketahui :(",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.phoneNumber)).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun setAdapter() {
        covidDetailAdapter = HospitalDetailCovidAdapter()
        binding.rvHospitalDetailCovid.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = covidDetailAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    binding.apply {
                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)

                                // if the recycler view is scrolled
                                // above hide the FAB
                                if (dy > 5 && fab.isShown) {
                                    fab.hide()
                                }

                                // if the recycler view is
                                // scrolled above show the FAB
                                if (dy < -5 && !fab.isShown) {
                                    fab.show()
                                }

                                // of the recycler view is at the first
                                // item always show the FAB
                                if (!recyclerView.canScrollVertically(-1)) {
                                    fab.show()
                                }
                            }
                        })
                    }
                }
            })
        }
    }

    private fun setViewModel() {
        covidDetailViewModel.dataDetailCovidHospital(Constant.hospitalId)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> progressBar(true)
                    is Resource.Success -> {
                        progressBar(false)
                        covidDetailAdapter.setData(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar(false)
                        dataEmpty()
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