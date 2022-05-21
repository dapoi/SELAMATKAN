package com.dafdev.selamatkan.view.adapter.hospital.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.DetailHospital
import com.dafdev.selamatkan.databinding.ItemListDetailHospitalBinding
import com.dafdev.selamatkan.utils.HelpUtil.formatDate

class HospitalDetailNonCovidAdapter :
    RecyclerView.Adapter<HospitalDetailNonCovidAdapter.HospitalDetailViewHolder>() {

    private val listHospitalNonCovidDetail = ArrayList<DetailHospital>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DetailHospital>) {
        with(listHospitalNonCovidDetail) {
            clear()
            addAll(data)
            sortWith { object1, object2 ->
                object1.bedName!!.compareTo(object2.bedName.toString())
            }
        }
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
        holder.bind(listHospitalNonCovidDetail[position])
    }

    override fun getItemCount(): Int = listHospitalNonCovidDetail.size

    inner class HospitalDetailViewHolder(private val binding: ItemListDetailHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailHospital) {
            with(binding) {
                data.apply {
                    tvDetailBedName.text = bedName
                    tvDetailTotalBed.text = totalBed.toString()
                    tvDetailAvailableBed.text = bedAble.toString()

                    val lastUpdateRoom = formatDate(
                        info.toString(),
                        "dd-MM-yyyy HH:mm:ss",
                        "dd MMM yyyy | HH:mm z"
                    )
                    tvInfo.text = lastUpdateRoom
                }
            }
        }
    }

}