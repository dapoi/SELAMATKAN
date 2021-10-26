package com.dafdev.selamatkan.view.adapter.hospital.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.remote.response.BedDetailItem
import com.dafdev.selamatkan.databinding.ItemDetailHospitalBinding

class HospitalDetailNonCovidAdapter :
    RecyclerView.Adapter<HospitalDetailNonCovidAdapter.HospitalDetailViewHolder>() {

    private val listHospitalNonCovidDetail = ArrayList<BedDetailItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BedDetailItem>) {
        listHospitalNonCovidDetail.clear()
        listHospitalNonCovidDetail.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalDetailViewHolder {
        return HospitalDetailViewHolder(
            ItemDetailHospitalBinding.inflate(
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
        holder.bind(listHospitalNonCovidDetail[position])
    }

    override fun getItemCount(): Int = listHospitalNonCovidDetail.size

    inner class HospitalDetailViewHolder(private val binding: ItemDetailHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BedDetailItem) {
            with(binding) {
                data.apply {
                    tvDetailBedName.text = stats?.title
                    tvDetailTotalBed.text = stats?.bed_available.toString()
                    tvDetailAvailableBed.text = stats?.bed_empty.toString()
                    tvInfo.text = time
                }
            }
        }
    }

}