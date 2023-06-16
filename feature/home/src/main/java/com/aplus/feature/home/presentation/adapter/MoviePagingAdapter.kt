package com.aplus.feature.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aplus.common.R
import com.aplus.common.databinding.RvMoviesBinding
import com.aplus.common.utils.MoviesHelper
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.bumptech.glide.Glide

/**
 *
 * This adapter use for displaying list of movies </br>
 * Displaying Movie Poster, Title, Rating, Overview, Genres and Favorite Button </br>
 *
 * @property items is List of Movies Data
 */
class MoviePagingAdapter(
    private var items: List<Movies>,
    private val onClickFavorite: (Movies, Int) -> Unit,
    private val onClickMovies: (Movies) -> Unit,
) : PagingDataAdapter<Movies, MoviePagingAdapter.ViewHolder>(ARTICLE_DIFF_CALLBACK) {

    private var listGenres: List<Genres> = listOf()
    private var listFavorite: MutableList<Movies> = mutableListOf()
    private var moviesHelper: MoviesHelper = MoviesHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvMoviesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(holder, items[position])

    inner class ViewHolder(val binding: RvMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(holder: ViewHolder, item: Movies) = with(binding) {
            if (!item.poster_path.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.image_url) + item.poster_path)
                    .error(R.drawable.no_image)
                    .into(poster)
            }
            title.text = item.title
            rating.text = item.vote_average.toString()
            overview.text = moviesHelper.limitOverview(item.overview)
            genres.text = moviesHelper.convertGenres(item.genre_ids, listGenres)
            if (listFavorite.any { it.id == item.id }) {
                favorite.setImageResource(R.drawable.ic_favorite_32)
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_border_32)
            }
            favorite.setOnClickListener {
                if (listFavorite.any { it.id == item.id }) {
                    favorite.setImageResource(R.drawable.ic_favorite_border_32)
                } else {
                    favorite.setImageResource(R.drawable.ic_favorite_32)
                }
                onClickFavorite(item, holder.adapterPosition)
            }
            mainCard.setOnClickListener { onClickMovies(item) }
            executePendingBindings()
        }
    }

    fun addData(data: List<Movies>) {
        val lastPosition = this.items.size
        this.items += data
        notifyItemRangeInserted(lastPosition, this.items.size - 1)
    }

    fun clearData() {
        val size = this.items.size
        this.items = listOf()
        notifyItemRangeRemoved(0, size)
    }

    fun removeData(movies: Movies, position: Int) {
        listFavorite.removeIf { it.id == movies.id }
        this.items = this.items.filter { it.id != movies.id }
        notifyItemRemoved(position)
    }

    fun setGenre(data: List<Genres>) {
        this.listGenres = data
    }

    fun setFavorite(data: List<Movies>) {
        this.listFavorite = data as MutableList<Movies>
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean =
                oldItem == newItem
        }
    }
}