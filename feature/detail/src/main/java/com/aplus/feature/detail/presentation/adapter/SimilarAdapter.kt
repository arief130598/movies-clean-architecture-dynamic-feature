package com.aplus.feature.detail.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aplus.domain.model.Movies
import com.aplus.common.R
import com.aplus.feature.detail.databinding.RvSimilarBinding
import com.aplus.feature.detail.presentation.ui.DetailFragment
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 *
 * This adapter use for displaying similar movies that opened in Detail Fragment
 *
 * @property items is List of Movies Data
 */
class SimilarAdapter(private var items: List<Movies>, private val fragment: Fragment) :
    RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvSimilarBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: RvSimilarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movies) {
            Glide.with(fragment)
                .load(fragment.getString(R.string.image_url) + item.poster_path)
                .error(R.drawable.no_image)
                .into(binding.poster)
            binding.title.text = item.title
            binding.mainCard.setOnClickListener {
                // Update UI Here
                (fragment as DetailFragment).setupUI(item)
            }
            binding.executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<Movies>){
        this.items = data
        notifyDataSetChanged()
    }
}