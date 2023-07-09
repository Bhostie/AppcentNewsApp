package com.appcentnewsapp.barisgokmen.ui.newsList

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.FragmentNewsBinding
import com.appcentnewsapp.barisgokmen.ui.newsDetails.NewsDetailsActivity
import com.appcentnewsapp.barisgokmen.ui.recycler.NewsRecyclerViewAdapter
import com.appcentnewsapp.barisgokmen.ui.recycler.RecyclerViewItemClickListener
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val searchDelay: Long = 1500 // 1.5 seconds delay
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

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
        setEditText()
        observeViewModel()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setEditText(){
        val searchEditText: TextInputEditText = binding.tieSearch
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cancel previous searchRunnable if exists
                searchRunnable?.let { handler.removeCallbacks(it) }
            }
            override fun afterTextChanged(s: Editable?) {
                // Create a new searchRunnable with delay
                searchRunnable = Runnable {
                    val query = s.toString()
                    if (query.isNotBlank()){
                        viewModel.callSearchQuery(query)
                    }
                }
                // Schedule the searchRunnable with delay
                handler.postDelayed(searchRunnable!!, searchDelay)
            }
        })
    }
    private fun observeViewModel(){
        viewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            newsListRecyclerViewAdapter.setNewsList(newsArticles)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            checkResultIsEmpty(errorMessage)
        }
    }
    private fun checkResultIsEmpty(errorMessage:String?){
        if (!errorMessage.isNullOrBlank()){
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = errorMessage
        } else {
            binding.tvErrorMessage.visibility = View.GONE
        }
    }
    private val recyclerViewItemClickListener = object : RecyclerViewItemClickListener<ArticlesItem>{
        override fun onClick(item: ArticlesItem?) {
            val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
            val itemJson = Gson().toJson(item)
            intent.putExtra("clickedItemJson", itemJson)
            startActivity(intent)
        }
    }
    private fun initializeRecyclerAdapter(){
        newsListRecyclerViewAdapter = NewsRecyclerViewAdapter(recyclerViewItemClickListener)
        binding.rvNewsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNewsList.adapter = newsListRecyclerViewAdapter
    }
}