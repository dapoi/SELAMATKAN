package com.dafdev.selamatkan.view.adapter.hospital.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.HospitalNonCovid
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding
import com.dafdev.selamatkan.utils.Constant

class HospitalNonCovidAdapter : RecyclerView.Adapter<HospitalNonCovidAdapter.NonCovidViewHolder>() {

    private val listHospital = ArrayList<HospitalNonCovid>()
    var onItemClick: ((HospitalNonCovid) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNonCovidHospital(data: List<HospitalNonCovid>) {
        listHospital.clear()
        listHospital.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NonCovidViewHolder {
        return NonCovidViewHolder(
            ItemListHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NonCovidViewHolder,
        position: Int
    ) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int = listHospital.size

    inner class NonCovidViewHolder(private val binding: ItemListHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalNonCovid) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvInfo.text = info
                    Constant.hospitalAddress = address!!
                    Constant.hospitalId = id!!
                    Constant.hospitalName = name!!
                    if (phone != null) {
                        Constant.phoneNumber = phone
                    } else {
                        Constant.phoneNumber = "-"
                    }
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHospital[absoluteAdapterPosition])
            }
        }
    }
}