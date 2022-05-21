package com.dafdev.selamatkan.view.adapter.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.databinding.ItemListNewsBinding
import com.dafdev.selamatkan.utils.HelpUtil.newsFormatDate
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<News>) {
        listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemListNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    inner class NewsViewHolder(private val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(data: News) {
            with(binding) {
                val shimmer =
                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                        .setBaseAlpha(0.7f) //the alpha of the underlying children
                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                        .setAutoStart(true)
                        .build()

                val shimmerDrawable = ShimmerDrawable().apply {
                    setShimmer(shimmer)
                }

                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .transform(RoundedCorners(20)).apply(
                        RequestOptions.placeholderOf(shimmerDrawable).error(R.drawable.ic_error)
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgNews)

                tvTitleNews.text = data.title
                if (data.content.isEmpty() || data.content == "null") {
                    tvContentNews.text = "Klik untuk melihat lebih detil"
                } else {
                    tvContentNews.text = data.content
                }

                tvDateNews.text = newsFormatDate(data.publishedAt)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }
    }
}