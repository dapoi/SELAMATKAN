package com.dafdev.selamatkan.view.adapter.hospital.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.DetailHospital
import com.dafdev.selamatkan.databinding.ItemListDetailHospitalBinding

class HospitalDetailCovidAdapter :
    RecyclerView.Adapter<HospitalDetailCovidAdapter.HospitalDetailViewHolder>() {

    private val listHospitalCovidDetail = ArrayList<DetailHospital>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DetailHospital>) {
        listHospitalCovidDetail.clear()
        listHospitalCovidDetail.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalDetailViewHolder {
        return HospitalDetailViewHolder(
            ItemListDetailHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HospitalDetailViewHolder,
        position: Int
    ) {
        holder.bind(listHospitalCovidDetail[position])
    }

    override fun getItemCount(): Int = listHospitalCovidDetail.size

    inner class HospitalDetailViewHolder(private val binding: ItemListDetailHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailHospital) {
            with(binding) {
                data.apply {
                    tvDetailBedName.text = bedName
                    tvDetailTotalBed.text = totalBed.toString()
                    tvDetailAvailableBed.text = bedAble.toString()
                    tvInfo.text = info
                }
            }
        }
    }
}