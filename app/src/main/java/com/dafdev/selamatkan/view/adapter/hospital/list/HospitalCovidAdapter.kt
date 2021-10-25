package com.dafdev.selamatkan.view.adapter.hospital.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.response.HospitalsCovidItem
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.HospitalDetailActivity

class HospitalCovidAdapter(private val context: Context) :
    RecyclerView.Adapter<HospitalCovidAdapter.CovidViewHolder>() {

    private val listHospital = ArrayList<HospitalsCovidItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCovidHospital(data: List<HospitalsCovidItem>) {
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
        fun bind(data: HospitalsCovidItem) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvHospitalAddress.text = address
                    if (phone == null) {
                        tvHospitalPhone.text = "-"
                    } else {
                        tvHospitalPhone.text = phone
                    }
                    tvInfo.text = info
                    cvHospital.setOnClickListener {
                        Constant.hospitalId = id!!
                        Constant.hospitalName = name!!
                        if (phone != null) {
                            Constant.phoneNumber = phone
                        } else {
                            Constant.phoneNumber = ""
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