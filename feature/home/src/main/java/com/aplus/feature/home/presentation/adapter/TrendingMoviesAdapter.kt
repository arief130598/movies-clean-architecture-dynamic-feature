package com.aplus.feature.home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplus.common.R
import com.aplus.common.utils.MoviesHelper
import com.aplus.domain.model.Movies
import com.aplus.feature.home.databinding.VpHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class TrendingMoviesAdapter(
    private var items: List<Movies>,
) : RecyclerView.Adapter<TrendingMoviesAdapter.ViewHolder>() {

    private var moviesHelper: MoviesHelper = MoviesHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VpHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    inner class ViewHolder(private val binding: VpHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movies) {
            Glide.with(itemView.context)
                .load(itemView.context.getString(R.string.background_image_url) + item.backdrop_path)
                .error(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivTrendMovies)

            binding.tvTrendTitle.text = item.title
            binding.tvTrendOverview.text = moviesHelper.limitOverview(item.overview)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<Movies>) {
        this.items = items
        notifyDataSetChanged()
    }
}