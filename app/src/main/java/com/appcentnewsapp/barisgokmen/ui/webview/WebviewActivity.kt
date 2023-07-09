package com.appcentnewsapp.barisgokmen.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.appcentnewsapp.barisgokmen.databinding.ActivityNewsDetailsBinding
import com.appcentnewsapp.barisgokmen.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {

    private var _binding: ActivityWebviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webview
        webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            builtInZoomControls = true
            displayZoomControls = false
        }
        webView.loadUrl(intent.getStringExtra("url").toString())

        binding.ibBackbutton.setOnClickListener {
            finish()
        }
    }
    override fun onPause() {
        super.onPause()
        webView.onPause()
    }
    override fun onResume() {
        super.onResume()
        webView.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}