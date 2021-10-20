package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.ItemProvinceUntilCityBinding

class ProvinceAdapter : RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    private val listProvince = ArrayList<ProvinceEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<ProvinceEntity>) {
        listProvince.clear()
        listProvince.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceAdapter.ProvinceViewHolder {
        val view =
            ItemProvinceUntilCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvinceAdapter.ProvinceViewHolder, position: Int) {
        val dataPosition = listProvince[position]
        holder.bind(dataPosition)
    }

    override fun getItemCount(): Int = listProvince.size

    inner class ProvinceViewHolder(private val binding: ItemProvinceUntilCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProvinceEntity) {
            with(binding) {
                tvTerritorial.text = data.name
            }
        }
    }
}