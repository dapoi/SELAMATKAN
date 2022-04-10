package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.databinding.ItemListNewsBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<News>) {
        listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder =
        NewsViewHolder(
            ItemListNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    inner class NewsViewHolder(private val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(data: News) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .transform(RoundedCorners(10)).apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgNews)

                tvTitleNews.text = data.title
                if (data.content.isNullOrEmpty()) {
                    tvContentNews.text = "Klik untuk melihat lebih detil"
                } else {
                    tvContentNews.text = data.content
                }

                val zdt = ZonedDateTime.parse(data.publishedAt)
                val resultDate = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", Locale.ENGLISH)
                tvDateNews.text = resultDate.format(zdt)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }
    }
}