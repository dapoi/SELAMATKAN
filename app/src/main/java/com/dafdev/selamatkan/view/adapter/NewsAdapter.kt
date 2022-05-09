package com.dafdev.selamatkan.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.databinding.ItemListNewsBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<NewsEntity>()
    var onItemClick: ((NewsEntity) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(data: List<NewsEntity>) {
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
        fun bind(data: NewsEntity) {
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
                    .fitCenter()
                    .transform(RoundedCorners(10)).apply(
                        RequestOptions.placeholderOf(shimmerDrawable).error(R.drawable.ic_error)
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
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

    private fun newsFormatDate(currentDate: String): String? {
        val currentFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val targetFormat = "dd MMM yyyy | HH:mm"
        val timeZone = "GMT"
        val id = Locale("in", "ID")
        val currentDateFormat: DateFormat = SimpleDateFormat(currentFormat, id)
        currentDateFormat.timeZone = TimeZone.getTimeZone(timeZone)
        val targetDateFormat: DateFormat = SimpleDateFormat(targetFormat, id)

        var targetDate: String? = null
        try {
            val date = currentDateFormat.parse(currentDate)
            if (date != null) {
                targetDate = targetDateFormat.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return targetDate
    }
}