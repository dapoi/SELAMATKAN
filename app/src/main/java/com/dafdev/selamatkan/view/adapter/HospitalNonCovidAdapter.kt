package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.response.HospitalsNonCovidItem
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding

class HospitalNonCovidAdapter : RecyclerView.Adapter<HospitalNonCovidAdapter.NonCovidViewHolder>() {

    private val listHospital = ArrayList<HospitalsNonCovidItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNonCovidHospital(data: List<HospitalsNonCovidItem>) {
        listHospital.clear()
        listHospital.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalNonCovidAdapter.NonCovidViewHolder {
        return NonCovidViewHolder(
            ItemListHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HospitalNonCovidAdapter.NonCovidViewHolder,
        position: Int
    ) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int = listHospital.size

    inner class NonCovidViewHolder(private val binding: ItemListHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalsNonCovidItem) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvHospitalAddress.text = address
                    tvHospitalBed.text = available_beds?.get(0)?.available.toString()
                    tvHospitalQueue.text = "0"
                    tvHospitalPhone.text = phone
                }
            }
        }
    }
}