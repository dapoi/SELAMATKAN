package com.dafdev.selamatkan.view.fragment.main

import android.annotation.SuppressLint
import android.content.Intent
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
import com.dafdev.selamatkan.data.source.response.ProvincesItem
import com.dafdev.selamatkan.databinding.FragmentHomeBinding
import com.dafdev.selamatkan.view.activity.main.ProvinceActivity
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.IndoDataCovidViewModel
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var provinceViewModel: ProvinceViewModel
    private lateinit var covidViewModel: IndoDataCovidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Calendar.getInstance().also {
                with(tvWeather) {
                    when (it.get(Calendar.HOUR_OF_DAY)) {
                        in 0..11 -> text = "Selamat Pagi"
                        in 12..15 -> text = "Selamat Siang"
                        in 16..18 -> text = "Selamat Sore"
                        in 19..24 -> text = "Selamat Malam"
                    }
                }
            }

            tvViewClear.setOnClickListener {
                startActivity(Intent(requireActivity(), ProvinceActivity::class.java))
            }
        }

        fStore = FirebaseFirestore.getInstance()

        fStore.collection("users").get().addOnSuccessListener {
            for (doc in it) {
                val dataName = doc["name"]
                binding.tvName.text = dataName.toString()
            }
        }
            .addOnFailureListener { exception ->
                Timber.tag("Find Fragment").w(exception, "Error getting documents.")
            }

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        provinceAdapter = ProvinceAdapter(requireActivity())
        binding.rvProvince.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = provinceAdapter
        }
    }

    private fun setViewModel() {
        val factoryDataCovid = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiCovid()))
        covidViewModel =
            ViewModelProvider(this, factoryDataCovid)[IndoDataCovidViewModel::class.java]
        covidViewModel.dataCovidIndo().observe(viewLifecycleOwner, {
            it.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.apply {
                            tvPositive.text = "..."
                            tvNegative.text = "..."
                            tvDeath.text = "..."
                        }
                    }
                    Status.SUCCESS -> {
                        binding.apply {
                            tvPositive.text = it.data?.positif?.toLong().toString()
                            tvNegative.text = it.data?.sembuh?.toLong().toString()
                            tvDeath.text = it.data?.meninggal?.toLong().toString()
                        }
                    }
                    Status.ERROR -> {
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

        val factoryProvince = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiHospital()))
        provinceViewModel = ViewModelProvider(this, factoryProvince)[ProvinceViewModel::class.java]
        provinceViewModel.getListProv().observe(viewLifecycleOwner, {
            it.let { resources ->
                when (resources.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        provinceAdapter.setProvinceAdapter(resources.data!! as List<ProvincesItem>)
                    }
                    Status.ERROR -> {
                        progressBar(false)
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
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

    companion object {
        private const val REQ_PERMISSION = 100
    }
}