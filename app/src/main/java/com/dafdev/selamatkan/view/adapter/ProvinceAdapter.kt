package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.remote.response.ProvincesItem
import com.dafdev.selamatkan.databinding.ItemProvinceUntilCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.CityActivity

class ProvinceAdapter(private val context: Context) :
    RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    private val listProvince = ArrayList<ProvincesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<ProvincesItem>) {
        listProvince.clear()
        listProvince.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceAdapter.ProvinceViewHolder {
        return ProvinceViewHolder(
            ItemProvinceUntilCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProvinceAdapter.ProvinceViewHolder, position: Int) {
        holder.bind(listProvince[position])
    }

    override fun getItemCount(): Int = listProvince.size

    inner class ProvinceViewHolder(private val binding: ItemProvinceUntilCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProvincesItem) {
            with(binding) {
                tvTerritorial.text = data.name
                cvTerritorial.setOnClickListener {
                    Constant.provinsiId = data.id!!
                    Intent(context, CityActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }
}