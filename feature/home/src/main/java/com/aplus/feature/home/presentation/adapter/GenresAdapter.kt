package com.aplus.feature.home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplus.domain.model.Genres
import com.aplus.feature.home.databinding.RvGenresBinding
import javax.inject.Inject

class GenresAdapter @Inject constructor(
    private var items: List<Genres>,
    private val onClickGenres: (String) -> Unit
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvGenresBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    inner class ViewHolder(private val binding: RvGenresBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Genres) = with(binding) {

            binding.genres.text = item.name
            binding.mainCard.setOnClickListener { onClickGenres(item.id.toString()) }
            executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Genres>) {
        this.items = data
        notifyDataSetChanged()
    }
}