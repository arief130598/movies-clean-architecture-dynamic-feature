package com.aplus.feature.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aplus.common.R
import com.aplus.domain.model.Reviews
import com.aplus.feature.detail.databinding.RvReviewBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class ReviewAdapter @Inject constructor(private var items: List<Reviews>, private var fragment: Fragment) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvReviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: RvReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Reviews) {
            Glide.with(fragment)
                .load(fragment.getString(R.string.avatar_url) + item.author_details.avatar_path)
                .error(R.drawable.no_image)
                .into(binding.avatar)
            binding.tvAuthor.text = item.author
            binding.tvDate.text = item.updated_at
            binding.tvReview.text = item.content
            binding.executePendingBindings()
        }
    }

    fun addData(data: List<Reviews>) {
        val lastPosition = this.items.size
        this.items += data
        notifyItemRangeInserted(lastPosition, this.items.size - 1)
    }

    fun clearData() {
        val size = this.items.size
        this.items = listOf()
        notifyItemRangeRemoved(0, size)
    }
}