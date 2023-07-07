package com.appcentnewsapp.barisgokmen.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.FragmentHomeBinding
import com.appcentnewsapp.barisgokmen.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
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
        val newsViewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.textDashboard

        viewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            val responseText = buildResponseText(newsArticles)
            textView.text = responseText
        }

        // Trigger the news search
        viewModel.searchNews("besiktas")

        // Rest of your fragment code...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildResponseText(newsArticles: List<ArticlesItem>): String {
        return newsArticles[0].toString()
    }
}