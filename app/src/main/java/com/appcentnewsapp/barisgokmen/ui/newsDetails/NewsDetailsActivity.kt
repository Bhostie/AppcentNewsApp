package com.appcentnewsapp.barisgokmen.ui.newsDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.ActivityNewsDetailsBinding
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class NewsDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private var articlesItem: ArticlesItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Getting clicked news information from intent
        val itemJson = intent.getStringExtra("clickedItemJson")
        articlesItem = Gson().fromJson(itemJson, ArticlesItem::class.java)


        binding.tvNewsauthor.text = articlesItem?.author
        binding.tvNewscontent.text = articlesItem?.content
        binding.tvDate.text = articlesItem?.publishedAt
        binding.tvNewstitle.text = articlesItem?.title
        Picasso.get().load(articlesItem?.urlToImage).into(binding.ivNewsimage)

        binding.ibBackbutton.setOnClickListener {
            finish()
        }



    }
}