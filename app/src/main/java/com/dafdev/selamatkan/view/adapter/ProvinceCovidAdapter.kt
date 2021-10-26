package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.remote.response.ProvinceCovidResponse
import com.dafdev.selamatkan.databinding.ItemListDataCovidProvBinding

class ProvinceCovidAdapter : RecyclerView.Adapter<ProvinceCovidAdapter.CovidViewHolder>() {

    private val listCovidProv = ArrayList<ProvinceCovidResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ProvinceCovidResponse>) {
        listCovidProv.clear()
        listCovidProv.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceCovidAdapter.CovidViewHolder {
        return CovidViewHolder(
            ItemListDataCovidProvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProvinceCovidAdapter.CovidViewHolder, position: Int) {
        holder.bind(listCovidProv[position])
    }

    override fun getItemCount(): Int = listCovidProv.size

    inner class CovidViewHolder(private val binding: ItemListDataCovidProvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProvinceCovidResponse) {
            with(binding) {
                data.apply {
                    tvProvince.text = provinsi
                    tvPositive.text = kasus.toString()
                    tvNegative.text = sembuh.toString()
                    tvDeath.text = meninggal.toString()
                }
            }
        }
    }
}