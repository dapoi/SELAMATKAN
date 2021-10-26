package com.dafdev.selamatkan.view.adapter.hospital.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.remote.response.HospitalsNonCovidItem
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.HospitalDetailActivity

class HospitalNonCovidAdapter(private val context: Context) :
    RecyclerView.Adapter<HospitalNonCovidAdapter.NonCovidViewHolder>() {

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
        fun bind(data: HospitalsNonCovidItem) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvHospitalAddress.text = address
                    if (phone == null) {
                        tvHospitalPhone.text = "-"
                    } else {
                        tvHospitalPhone.text = phone
                    }
                    tvInfo.text = available_beds?.get(0)?.info
                    cvHospital.setOnClickListener {
                        Constant.hospitalId = id!!
                        Constant.hospitalName = name!!
                        if (phone != null) {
                            Constant.phoneNumber = phone
                        }
                        Intent(context, HospitalDetailActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }
                }
            }
        }
    }
}