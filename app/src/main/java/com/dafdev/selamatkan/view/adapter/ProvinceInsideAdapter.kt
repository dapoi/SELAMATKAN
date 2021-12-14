package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.Province
import com.dafdev.selamatkan.databinding.ItemListAreaBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.CityActivity

class ProvinceInsideAdapter(private val context: Context) :
    RecyclerView.Adapter<ProvinceInsideAdapter.ProvinceViewHolder>() {

    private val listProvince = ArrayList<Province>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<Province>) {
        listProvince.clear()
        listProvince.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceInsideAdapter.ProvinceViewHolder {
        return ProvinceViewHolder(
            ItemListAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProvinceInsideAdapter.ProvinceViewHolder, position: Int) {
        holder.bind(listProvince[position])
    }

    override fun getItemCount(): Int = listProvince.size

    inner class ProvinceViewHolder(private val binding: ItemListAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Province) {
            with(binding) {
                tvTerritorial.text = data.name
                cvTerritorial.setOnClickListener {
                    Constant.provinceId = data.id!!
                    Intent(context, CityActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }
}