package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.Cities
import com.dafdev.selamatkan.databinding.ItemListAreaBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val listCity = ArrayList<Cities>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    internal fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCityAdapter(data: List<Cities>) {
        with(listCity) {
            clear()
            addAll(data)
            sortWith { object1, object2 ->
                object1.name!!.compareTo(object2.name.toString())
            }
        }
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
        @SuppressLint("SetTextI18n")
        fun bind(data: Cities) {
            with(binding) {
                data.name.let {
                    if (it == "Bekasi") {
                        tvTerritorial.text = "Bekasi Kabupaten"
                    } else {
                        tvTerritorial.text = it
                    }
                }

                cvTerritorial.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Cities)
    }
}