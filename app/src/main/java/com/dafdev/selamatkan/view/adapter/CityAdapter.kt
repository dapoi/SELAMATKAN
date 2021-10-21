package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.response.CitiesItem
import com.dafdev.selamatkan.databinding.ItemProvinceUntilCityBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val listCity = ArrayList<CitiesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCityAdapter(data: List<CitiesItem>) {
        listCity.clear()
        listCity.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        val view =
            ItemProvinceUntilCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
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
            }
        }
    }
}