package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val listNews = ArrayList<NewsEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<NewsEntity>) {
        listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder =
        NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .transform(RoundedCorners(10))
                    .into(imgNews)

                tvTitleNews.text = data.title
                tvContentNews.text = data.content
                tvDateNews.text = data.publishedAt
            }
        }
    }
}