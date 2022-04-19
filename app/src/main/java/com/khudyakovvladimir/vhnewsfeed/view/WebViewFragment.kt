package com.khudyakovvladimir.vhnewsfeed.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.application.appComponent
import com.khudyakovvladimir.vhnewsfeed.utils.AnimationHelper
import javax.inject.Inject

class WebViewFragment: Fragment() {

    lateinit var webView: WebView

    @Inject
    lateinit var animationHelper: AnimationHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectWebViewFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.web_view_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webView)
        animationHelper.fadeInView(webView, 1500)

        val url = arguments?.getString("url","")
        Log.d("TAG", "WebViewFragment -  onViewCreated() -  url = $url")

        webView.loadUrl("file:///android_asset/loadImage.html")

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Log.d("TAG", "WebViewFragment -  onReceivedError()")
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.d("TAG", "WebViewFragment - onReceivedHttpError")
            }
        }

        webView.loadUrl(url!!)
    }
}