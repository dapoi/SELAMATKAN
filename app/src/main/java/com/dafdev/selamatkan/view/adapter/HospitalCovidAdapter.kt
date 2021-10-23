package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.response.HospitalsCovidItem
import com.dafdev.selamatkan.databinding.ItemListCovidHospitalBinding

class HospitalCovidAdapter : RecyclerView.Adapter<HospitalCovidAdapter.CovidViewHolder>() {

    private val listHospital = ArrayList<HospitalsCovidItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCovidHospital(data: List<HospitalsCovidItem>) {
        listHospital.clear()
        listHospital.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidViewHolder {
        return CovidViewHolder(
            ItemListCovidHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CovidViewHolder, position: Int) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int = listHospital.size

    inner class CovidViewHolder(private val binding: ItemListCovidHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalsCovidItem) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvHospitalAddress.text = address
                    tvHospitalBed.text = bed_availability.toString()
                    tvHospitalQueue.text = queue.toString()
                    if (phone == null) {
                        tvHospitalPhone.text = "-"
                    } else {
                        tvHospitalPhone.text = phone
                    }
                    tvInfo.text = info
                }
            }
        }
    }
}