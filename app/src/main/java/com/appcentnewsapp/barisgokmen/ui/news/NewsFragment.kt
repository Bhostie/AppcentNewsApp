package com.appcentnewsapp.barisgokmen.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.FragmentHomeBinding
import com.appcentnewsapp.barisgokmen.databinding.FragmentNewsBinding
import com.appcentnewsapp.barisgokmen.ui.recycler.NewsRecyclerViewAdapter
import com.appcentnewsapp.barisgokmen.ui.recycler.RecyclerViewItemClickListener

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsRecyclerViewAdapter

    private var _binding: FragmentNewsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerAdapter()

        viewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            newsListRecyclerViewAdapter.setNewsList(newsArticles)
        }

        // Trigger the news search
        viewModel.searchNews("ODTÜ")



        // Rest of your fragment code...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildResponseText(newsArticles: List<ArticlesItem>?): String {
        return newsArticles?.get(0)?.description.toString()
    }

    private val recyclerViewItemClickListener = object : RecyclerViewItemClickListener<ArticlesItem>{
        override fun onClick(item: ArticlesItem?) {
            //TODO: Navigation to news details
        }
    }


    private fun initializeRecyclerAdapter(){
        newsListRecyclerViewAdapter = NewsRecyclerViewAdapter(recyclerViewItemClickListener)
        binding?.rvNewsList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvNewsList?.adapter = newsListRecyclerViewAdapter
    }



}