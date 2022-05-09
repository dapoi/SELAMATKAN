package com.dafdev.selamatkan.view.adapter.hospital.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dafdev.selamatkan.data.domain.model.HospitalNonCovid
import com.dafdev.selamatkan.databinding.ItemListHospitalBinding

class HospitalNonCovidAdapter : RecyclerView.Adapter<HospitalNonCovidAdapter.NonCovidViewHolder>(),
    Filterable {

    private var listHospital = ArrayList<HospitalNonCovid>()
    private var listHospitalFilter = ArrayList<HospitalNonCovid>()
    var onItemClick: ((HospitalNonCovid) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNonCovidHospital(data: List<HospitalNonCovid>) {
        listHospital = data as ArrayList<HospitalNonCovid>
        listHospitalFilter = listHospital
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NonCovidViewHolder {
        return NonCovidViewHolder(
            ItemListHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NonCovidViewHolder,
        position: Int
    ) {
        holder.bind(listHospitalFilter[position])
    }

    override fun getItemCount(): Int = listHospitalFilter.size

    inner class NonCovidViewHolder(private val binding: ItemListHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalNonCovid) {
            with(binding) {
                data.apply {
                    tvHospitalName.text = name
                    tvInfo.text = info
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHospital[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                listHospitalFilter = if (charString.isEmpty()) {
                    listHospital
                } else {
                    val filteredList = ArrayList<HospitalNonCovid>()
                    listHospital.filter {
                        (it.name!!.lowercase().contains(constraint.toString().lowercase()))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply {
                    values = listHospitalFilter
                }
            }

            @Suppress("UNCHECKED_CAST")
            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraints: CharSequence?, result: FilterResults?) {
                listHospitalFilter = if (result?.values == null) {
                    ArrayList()
                } else {
                    result.values as ArrayList<HospitalNonCovid>
                }
                notifyDataSetChanged()
            }
        }
    }
}