package com.dafdev.selamatkan.view.fragment.main.hospital.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.network.ApiConfig
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.hospital.detail.HospitalDetailNonCovidAdapter
import com.dafdev.selamatkan.viewmodel.DetailNonCovidHospitalViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status
import com.google.android.material.snackbar.Snackbar

class NonCovidHospitalDetailFragment : Fragment() {

    private lateinit var binding: FragmentNonCovidHospitalDetailBinding
    private lateinit var nonCovidDetailAdapter: HospitalDetailNonCovidAdapter
    private lateinit var nonCovidDetailViewModel: DetailNonCovidHospitalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNonCovidHospitalDetailBinding.inflate(inflater, container, false)
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
        nonCovidDetailAdapter = HospitalDetailNonCovidAdapter()
        binding.rvHospitalDetailNonCovid.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = nonCovidDetailAdapter

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
    }

    private fun setViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiHospital()))
        nonCovidDetailViewModel = ViewModelProvider(
            this,
            factory
        )[DetailNonCovidHospitalViewModel::class.java]
        nonCovidDetailViewModel.dataDetailNonCovidHospital(Constant.hospitalId)
            .observe(viewLifecycleOwner, {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> progressBar(true)
                        Status.SUCCESS -> {
                            progressBar(false)
                            if (resource.data.isNullOrEmpty()) {
                                dataEmpty()
                            } else {
                                nonCovidDetailAdapter.setData(resource.data)
                            }
                        }
                        Status.ERROR -> {
                            progressBar(false)
                            Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
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