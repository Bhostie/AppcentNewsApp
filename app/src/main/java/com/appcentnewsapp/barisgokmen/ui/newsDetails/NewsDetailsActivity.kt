package com.appcentnewsapp.barisgokmen.ui.newsDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.appcentnewsapp.barisgokmen.R
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.databinding.ActivityNewsDetailsBinding
import com.appcentnewsapp.barisgokmen.ui.webview.WebviewActivity
import com.appcentnewsapp.barisgokmen.utils.SharedPreferencesManager
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

        prepareUI()
        checkLiked()
        likeButtonClickListener()
        sourceButtonClickListener()

        binding.ibBackbutton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    fun checkLiked(): Boolean{
        if (SharedPreferencesManager.isArticleSaved(articlesItem?.url)) {
            binding.ibFavoritebutton.setImageResource(R.drawable.ic_favorite)
            return true
        }
        else {
            binding.ibFavoritebutton.setImageResource(R.drawable.ic_favorite_border)
            return false
        }
    }

    private fun sourceButtonClickListener(){
        binding.btnSource.setOnClickListener{
            val intent = intent
            intent.setClass(this, WebviewActivity::class.java)
            intent.putExtra("url", articlesItem?.url)
            startActivity(intent)
        }
    }

    private fun likeButtonClickListener(){
        binding.ibFavoritebutton.setOnClickListener{

            if(checkLiked()){
                SharedPreferencesManager.removeArticle(articlesItem?.url)
                Log.d("LIKES","TRUE")
            }
            else{
                SharedPreferencesManager.putArticle(articlesItem?.url, articlesItem)
                Log.d("LIKES","FALSE")
            }
            checkLiked()
        }
    }

    private fun prepareUI(){
        binding.tvNewsauthor.text = articlesItem?.author
        binding.tvNewscontent.text = articlesItem?.content
        binding.tvDate.text = articlesItem?.publishedAt
        binding.tvNewstitle.text = articlesItem?.title
        Picasso.get().load(articlesItem?.urlToImage).into(binding.ivNewsimage)
    }

}