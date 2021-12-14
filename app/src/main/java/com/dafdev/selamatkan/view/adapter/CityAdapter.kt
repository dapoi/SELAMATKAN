package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.Cities
import com.dafdev.selamatkan.databinding.ItemListAreaBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.activity.main.HospitalActivity

class CityAdapter(private val context: Context) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val listCity = ArrayList<Cities>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCityAdapter(data: List<Cities>) {
        listCity.clear()
        listCity.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        return CityViewHolder(
            ItemListAreaBinding.inflate(
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

    inner class CityViewHolder(private val binding: ItemListAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Cities) {
            with(binding) {
                tvTerritorial.text = data.name
                cvTerritorial.setOnClickListener {
                    Constant.cityId = data.id!!
                    Intent(context, HospitalActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }
}