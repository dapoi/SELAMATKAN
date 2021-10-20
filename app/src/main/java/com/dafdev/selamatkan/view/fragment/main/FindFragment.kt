package com.dafdev.selamatkan.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentFindBinding
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import java.util.*

class FindFragment : Fragment() {

    private lateinit var binding: FragmentFindBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var provinceVIewModel: ProvinceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindBinding.inflate(inflater, container, false)
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
        provinceAdapter = ProvinceAdapter()
        binding.rvProvince.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = provinceAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        provinceVIewModel = ViewModelProvider(this, factory)[ProvinceViewModel::class.java]
        provinceVIewModel.dataProvinces.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> progressBar(false)
                    is Resource.Success -> {
                        progressBar(true)
                        provinceAdapter.setProvinceAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar(false)
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
    }
}