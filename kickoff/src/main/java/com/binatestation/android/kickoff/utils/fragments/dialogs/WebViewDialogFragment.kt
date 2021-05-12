/*
 * (c) Binate Station Private Limited. All rights reserved.
 */
package com.binatestation.android.kickoff.utils.fragments.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.widget.ContentLoadingProgressBar
import com.binatestation.android.kickoff.R

/**
 * A dialog fragment to show the given url in web view.
 */
open class WebViewDialogFragment : BaseDialogFragment() {

    private var url: String? = null
    private var mTitle: String? = null
    private var webView: WebView? = null
    private var progressBar: ContentLoadingProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey(KEY_URL) }?.apply { url = this.getString(KEY_URL) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view_dialog, container, false)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.web_view)
        progressBar = view.findViewById(R.id.progress_bar)
        webView?.settings?.javaScriptEnabled = true        // enable javascript just test
        webView?.settings?.setAppCacheEnabled(true)
        webView?.settings?.setAppCachePath(context?.cacheDir?.path)
        webView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT

        webView?.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                progressBar?.hide()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar?.hide()
            }
        }

        loadUrl()
    }

    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(mTitle)) {
            try {
                activity?.title = mTitle
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    /**
     * loads the web url
     */
    private fun loadUrl() {
        url?.takeIf { it.isNotEmpty() }?.apply {
            webView?.loadUrl(this)
        }
    }

    @Suppress("unused")
            /**
             * sets the web url
             *
             * @param url url to load
             */
    fun setUrl(url: String) {
        this.url = url
        loadUrl()
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    fun setTitle(title: String) {
        mTitle = title
    }

    companion object {

        private const val TAG = "WebViewDialogFragment"

        private const val KEY_URL = "URL"

        /**
         * gets the instance of WebViewDialogFragment
         *
         * @param url String
         * @return WebViewDialogFragment
         */
        fun newInstance(url: String): WebViewDialogFragment {
            Log.d(TAG, "newInstance() called with: url = [$url]")
            val args = Bundle()
            args.putString(KEY_URL, url)
            val fragment = WebViewDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
