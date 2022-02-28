package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.ItemListAreaBinding

class ProvinceAdapter : RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    private val listProvince = ArrayList<ProvinceEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    internal fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<ProvinceEntity>) {
        with(listProvince) {
            clear()
            addAll(data)
            sortWith { object1, object2 ->
                object1.name.compareTo(object2.name)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceAdapter.ProvinceViewHolder {
        return ProvinceViewHolder(
            ItemListAreaBinding.inflate(
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

    inner class ProvinceViewHolder(private val binding: ItemListAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProvinceEntity) {
            with(binding) {
                tvTerritorial.text = data.name
                cvTerritorial.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ProvinceEntity)
    }
}