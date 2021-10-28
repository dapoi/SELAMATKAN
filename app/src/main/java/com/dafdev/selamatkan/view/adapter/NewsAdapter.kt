package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.databinding.ItemNewsBinding
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val listNews = ArrayList<News>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<News>) {
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
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(data: News) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .transform(RoundedCorners(10))
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

                val factory = ViewModelFactory.getInstance(context)
                val newsViewModel = ViewModelProvider(
                    ::ViewModelStore,
                    factory
                )[NewsViewModel::class.java]
                var statusFav = data.isFav
                setStatusFav(statusFav)
                buttonFav.setOnClickListener {
                    statusFav = !statusFav
                    newsViewModel.updateFavNews(data, statusFav)
                    setStatusFav(statusFav)
                }
            }
        }

        private fun setStatusFav(statusFav: Boolean) {
            if (statusFav) {
                binding.buttonFav.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_fav_true)
                )
            } else {
                binding.buttonFav.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_fav_false)
                )
            }
        }
    }
}