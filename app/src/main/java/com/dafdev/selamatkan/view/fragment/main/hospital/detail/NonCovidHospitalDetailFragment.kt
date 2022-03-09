package com.dafdev.selamatkan.view.fragment.main.hospital.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.databinding.FragmentNonCovidHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.dataEmpty
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.activity.main.MapActivity
import com.dafdev.selamatkan.view.adapter.hospital.detail.HospitalDetailNonCovidAdapter
import com.dafdev.selamatkan.viewmodel.DetailNonCovidHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NonCovidHospitalDetailFragment : Fragment() {

    private var _binding: FragmentNonCovidHospitalDetailBinding? = null
    private val binding get() = _binding!!

    private val nonCovidDetailViewModel: DetailNonCovidHospitalViewModel by viewModels()

    private lateinit var nonCovidDetailAdapter: HospitalDetailNonCovidAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNonCovidHospitalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()

        binding.fab.setOnClickListener {
            startActivity(Intent(requireActivity(), MapActivity::class.java))
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
        nonCovidDetailViewModel.dataDetailNonCovidHospital(Constant.hospitalId)
            .observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        is Resource.Loading -> progressBar.showProgressBar(true)
                        is Resource.Success -> {
                            progressBar.showProgressBar(false)
                            nonCovidDetailAdapter.setData(it.data!!)
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