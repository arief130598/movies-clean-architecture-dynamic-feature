package com.aplus.common.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplus.common.R
import com.aplus.common.databinding.RvMoviesBinding
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 *
 * This adapter use for displaying list of movies </br>
 * Displaying Movie Poster, Title, Rating, Overview, Genres and Favorite Button </br>
 *
 * @property items is List of Movies Data
 */
class MovieAdapter @Inject constructor(
    private var items: List<Movies>,
    private val onClickFavorite: (Movies) -> Unit,
    private val onClickMovies: (Movies) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var listGenres: List<Genres> = listOf()
    private var listFavorite: MutableList<Movies> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvMoviesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)

    inner class ViewHolder(val binding: RvMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movies, position: Int) = with(binding) {
            if(!item.poster_path.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.image_url) + item.poster_path)
                    .error(R.drawable.no_image)
                    .into(poster)
            }
            title.text = item.title
            rating.text = item.vote_average.toString()
            overview.text = limitOverview(item.overview)
            genres.text = convertGenres(item.genre_ids)
            if(listFavorite.any { it.id == item.id }){
                favorite.setImageResource(R.drawable.ic_favorite_32)
            }else{
                favorite.setImageResource(R.drawable.ic_favorite_border_32)
            }
            favorite.setOnClickListener{
                if(listFavorite.any { it.id == item.id }){
                    favorite.setImageResource(R.drawable.ic_favorite_border_32)
                }else{
                    favorite.setImageResource(R.drawable.ic_favorite_32)
                }
                onClickFavorite(item) 
            }
            mainCard.setOnClickListener { onClickMovies(item) }
            executePendingBindings()
        }
    }

    fun addData(data : List<Movies>){
        val lastPosition = this.items.size
        this.items += data
        notifyItemRangeInserted(lastPosition, this.items.size-1)
    }

    fun setGenre(data : List<Genres>){
        this.listGenres = data
    }

    fun setFavorite(data : List<Movies>){
        this.listFavorite = data as MutableList<Movies>
    }

    fun changeFavorite(movies: Movies, position: Int){
        if(listFavorite.contains(movies)){
            listFavorite.remove(movies)
        }else{
            listFavorite.add(movies)
        }
        notifyItemChanged(position)
    }
    
    fun limitOverview(data: String): String{
        return if(data.length > 100) {
            var overview = data.substring(0, 100)
            if (overview[overview.length - 1] != '.') {
                overview = "$overview..."
            }
            overview
        }else{
            data
        }
    }
    
    fun convertGenres(data: List<Int>): String{
        var genres = ""
        return if(data.isNotEmpty()) {
            data.forEach {
                val item = this.listGenres.filter { x -> x.id == it }
                genres += if (item.isNotEmpty()) {
                    "${item[0].name}, "
                }else{
                    "${it}, "
                }
            }
            if(genres.length > 2) {
                genres.substring(0, genres.length-2)
            }else{
                genres
            }
        }else{
            genres
        }
    }
}