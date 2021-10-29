package com.dafdev.selamatkan.view.fragment.main.bottom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentHomeBinding
import com.dafdev.selamatkan.utils.SharedPref
import com.dafdev.selamatkan.view.activity.main.ProvinceActivity
import com.dafdev.selamatkan.view.activity.main.ProvinceCovidActivity
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.IndoDataCovidViewModel
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var provinceViewModel: ProvinceViewModel
    private lateinit var covidViewModel: IndoDataCovidViewModel
    private lateinit var pref: SharedPref

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

            tvViewAll.setOnClickListener {
                startActivity(Intent(requireActivity(), ProvinceCovidActivity::class.java))
            }

            tvViewClear.setOnClickListener {
                startActivity(Intent(requireActivity(), ProvinceActivity::class.java))
            }
        }
        fStore = FirebaseFirestore.getInstance()
        pref = SharedPref(requireActivity())

        pref.getUser().name.let {
            binding.tvName.text = it
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
        val factory = ViewModelFactory.getInstance(requireActivity())
        covidViewModel = ViewModelProvider(this, factory)[IndoDataCovidViewModel::class.java]
        covidViewModel.dataCovidIndo().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.apply {
                        tvPositive.text = "..."
                        tvNegative.text = "..."
                        tvDeath.text = "..."
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        tvPositive.text = it.data?.positif?.toLong().toString()
                        tvNegative.text = it.data?.sembuh?.toLong().toString()
                        tvDeath.text = it.data?.meninggal?.toLong().toString()
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                }
            }
        })

        provinceViewModel = ViewModelProvider(this, factory)[ProvinceViewModel::class.java]
        provinceViewModel.getListProv().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> progressBar(true)
                    is Resource.Success -> {
                        progressBar(false)
                        provinceAdapter.setProvinceAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
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