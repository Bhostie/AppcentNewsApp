package com.appcentnewsapp.barisgokmen.ui.recycler

import androidx.recyclerview.widget.RecyclerView
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<ArticlesItem>?
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ArticlesItem?) {
        binding.tvTitle.text = item?.title
        binding.tvDescription.text = item?.description
        Picasso
            .get()
            .load(item?.urlToImage)
            .into(binding.ivCover)

        binding.root.setOnClickListener {
            recyclerViewItemClickListener?.onClick(item)
        }
    }
}