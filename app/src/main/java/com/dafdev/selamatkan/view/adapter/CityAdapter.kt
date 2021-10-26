package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.remote.response.CitiesItem
import com.dafdev.selamatkan.databinding.ItemProvinceUntilCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.HospitalActivity

class CityAdapter(private val context: Context) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val listCity = ArrayList<CitiesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCityAdapter(data: List<CitiesItem>) {
        listCity.clear()
        listCity.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        return CityViewHolder(
            ItemProvinceUntilCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {
        val dataPosition = listCity[position]
        holder.bind(dataPosition)
    }

    override fun getItemCount(): Int = listCity.size

    inner class CityViewHolder(private val binding: ItemProvinceUntilCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CitiesItem) {
            with(binding) {
                tvTerritorial.text = data.name
                cvTerritorial.setOnClickListener {
                    Constant.kotaId = data.id!!
                    Intent(context, HospitalActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }
}