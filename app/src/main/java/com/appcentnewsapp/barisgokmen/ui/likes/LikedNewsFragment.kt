package com.appcentnewsapp.barisgokmen.ui.likes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.FragmentLikedNewsBinding
import com.appcentnewsapp.barisgokmen.ui.newsDetails.NewsDetailsActivity
import com.appcentnewsapp.barisgokmen.ui.recycler.NewsRecyclerViewAdapter
import com.appcentnewsapp.barisgokmen.ui.recycler.RecyclerViewItemClickListener
import com.google.gson.Gson


class LikedNewsFragment : Fragment() {

    private lateinit var viewModel: LikedNewsViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var _binding: FragmentLikedNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LikedNewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLikedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerAdapter()
        viewModel.getLocalNews()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalNews()
    }


    private fun observeViewModel() {
        viewModel.likedNewsList.observe(viewLifecycleOwner) { newsList ->
            newsListRecyclerViewAdapter.setNewsList(newsList)
        }
    }

    private val recyclerViewItemClickListener = object :
        RecyclerViewItemClickListener<ArticlesItem> {
        override fun onClick(item: ArticlesItem?) {
            Log.d("NewsFragment", "Clicked item: ${item?.title}")
            val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
            val itemJson = Gson().toJson(item)
            intent.putExtra("clickedItemJson", itemJson)
            startActivity(intent)
        }
    }

    private fun initializeRecyclerAdapter() {
        newsListRecyclerViewAdapter = NewsRecyclerViewAdapter(recyclerViewItemClickListener)
        binding.rvNewsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNewsList.adapter = newsListRecyclerViewAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}