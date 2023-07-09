package com.appcentnewsapp.barisgokmen.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.ItemNewsBinding

class NewsRecyclerViewAdapter(
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<ArticlesItem>?
) : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsList: List<ArticlesItem>? = null

    fun setNewsList(newsList: List<ArticlesItem>?){
        this.newsList = newsList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, recyclerViewItemClickListener)
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList?.getOrNull(position)
        holder.bind(item)
    }
    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }
}