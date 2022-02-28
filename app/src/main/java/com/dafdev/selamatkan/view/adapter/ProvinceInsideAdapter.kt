package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.Province
import com.dafdev.selamatkan.databinding.ItemListAreaBinding

class ProvinceInsideAdapter : RecyclerView.Adapter<ProvinceInsideAdapter.ProvinceViewHolder>() {

    private val listProvince = ArrayList<Province>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    internal fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<Province>) {
        with(listProvince) {
            clear()
            addAll(data)
            sortWith { object1, object2 ->
                object1.name!!.compareTo(object2.name.toString())
            }
        }
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
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Province)
    }
}