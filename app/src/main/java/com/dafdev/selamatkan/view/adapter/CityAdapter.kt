package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.Cities
import com.dafdev.selamatkan.databinding.ItemListAreaBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>(), Filterable {

    private var listCity = ArrayList<Cities>()
    private var listCityFilter = ArrayList<Cities>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    internal fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCityAdapter(data: List<Cities>) {
        listCity = data as ArrayList<Cities>
        listCity.sortWith { object1, object2 -> object1.name!!.compareTo(object2.name!!) }
        listCityFilter = listCity
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
        val dataPosition = listCityFilter[position]
        holder.bind(dataPosition)
    }

    override fun getItemCount(): Int = listCityFilter.size

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

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                listCityFilter = if (charString.isEmpty()) {
                    listCity
                } else {
                    val filteredList = ArrayList<Cities>()
                    listCity.filter {
                        (it.name!!.lowercase().contains(constraint.toString().lowercase()))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply {
                    values = listCityFilter
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraints: CharSequence?, result: FilterResults?) {
                listCityFilter = if (result?.values == null) {
                    ArrayList()
                } else {
                    result.values as ArrayList<Cities>
                }
                notifyDataSetChanged()
            }

        }
    }
}