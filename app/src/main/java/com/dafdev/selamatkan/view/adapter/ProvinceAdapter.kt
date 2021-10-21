package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.response.ProvincesItem
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
        val view =
            ItemProvinceUntilCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvinceAdapter.ProvinceViewHolder, position: Int) {
        holder.tvProvince.text = listProvince[position].name
        holder.cvProvince.setOnClickListener {
            Constant.provinsiId = listProvince[position].id!!
            Constant.provinsiName = listProvince[position].name!!
            Intent(context, CityActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int = listProvince.size

    inner class ProvinceViewHolder(binding: ItemProvinceUntilCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var cvProvince = binding.cvTerritorial
        var tvProvince = binding.tvTerritorial
    }
}