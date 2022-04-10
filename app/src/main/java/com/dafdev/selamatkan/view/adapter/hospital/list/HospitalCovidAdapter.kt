package com.dafdev.selamatkan.view.adapter.hospital.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.HospitalCovid
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding

class HospitalCovidAdapter : RecyclerView.Adapter<HospitalCovidAdapter.CovidViewHolder>() {

    private val listHospital = ArrayList<HospitalCovid>()
    var onItemCLick: ((HospitalCovid) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCovidHospital(data: List<HospitalCovid>) {
        listHospital.clear()
        listHospital.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidViewHolder {
        return CovidViewHolder(
            ItemListHospitalBinding.inflate(
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

    inner class CovidViewHolder(private val binding: ItemListHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalCovid) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvInfo.text = info
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemCLick?.invoke(listHospital[adapterPosition])
            }
        }
    }
}