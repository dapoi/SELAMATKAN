package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.CovidProv
import com.dafdev.selamatkan.databinding.ItemListDataCovidProvBinding
import com.dafdev.selamatkan.view.adapter.ProvinceCovidAdapter.CovidViewHolder

class ProvinceCovidAdapter : RecyclerView.Adapter<CovidViewHolder>(), Filterable {

    private var listCovidProv = ArrayList<CovidProv>()
    private var listCovidProvFiltered = ArrayList<CovidProv>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CovidProv>) {
        listCovidProv = data as ArrayList<CovidProv>
        listCovidProv.sortWith(compareBy { it.name })
        listCovidProvFiltered = listCovidProv
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CovidViewHolder {
        return CovidViewHolder(
            ItemListDataCovidProvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CovidViewHolder, position: Int) {
        holder.bind(listCovidProvFiltered[position])
    }

    override fun getItemCount(): Int = listCovidProvFiltered.size

    inner class CovidViewHolder(private val binding: ItemListDataCovidProvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CovidProv) {
            with(binding) {
                data.apply {
                    tvProvince.text = name
                    tvPositive.text = infected.toString()
                    tvNegative.text = recovered.toString()
                    tvDeath.text = fatal.toString()
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listCovidProvFiltered = if (charSearch.isEmpty()) {
                    listCovidProv
                } else {
                    val resultList = ArrayList<CovidProv>()
                    for (row in listCovidProv) {
                        if (row.name?.lowercase()!!.contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listCovidProvFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listCovidProvFiltered = results?.values as ArrayList<CovidProv>
                notifyDataSetChanged()
            }
        }
    }
}