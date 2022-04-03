package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.databinding.ItemListAreaBinding
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter.ProvinceViewHolder

class ProvinceAdapter : RecyclerView.Adapter<ProvinceViewHolder>(), Filterable {
    private var listProvince = ArrayList<ProvinceEntity>()
    private var listProvinceFiltered = ArrayList<ProvinceEntity>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    internal fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProvinceAdapter(data: List<ProvinceEntity>) {
        listProvince = data as ArrayList<ProvinceEntity>
        listProvince.sortWith { object1, object2 -> object1.name.compareTo(object2.name) }
        listProvinceFiltered = listProvince
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceViewHolder {
        return ProvinceViewHolder(
            ItemListAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.bind(listProvinceFiltered[position])
    }

    override fun getItemCount(): Int = listProvinceFiltered.size

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

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                listProvinceFiltered = if (charString.isEmpty()) {
                    listProvince
                } else {
                    val filteredList = ArrayList<ProvinceEntity>()
                    listProvince.filter {
                        (it.name.lowercase().contains(constraint.toString().lowercase()))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply {
                    values = listProvinceFiltered
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listProvinceFiltered = if (results?.values == null) {
                    ArrayList()
                } else {
                    results.values as ArrayList<ProvinceEntity>
                }
                notifyDataSetChanged()
            }
        }
    }
}