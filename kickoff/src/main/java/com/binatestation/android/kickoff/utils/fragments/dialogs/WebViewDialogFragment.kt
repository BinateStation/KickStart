/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last Updated at 28/8/20 3:47 PM.
 */

package com.binatestation.android.kickoff.utils.fragments.dialogs


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.binatestation.android.kickoff.R
import kotlinx.android.synthetic.main.fragment_web_view_dialog.*


/**
 * A dialog fragment to show the given url in web view.
 */
open class WebViewDialogFragment : BaseDialogFragment() {

    private var url: String? = null
    private var mTitle: String? = null

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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        web_view?.settings?.javaScriptEnabled = true        // enable javascript just test
        web_view?.settings?.setAppCacheEnabled(true)
        web_view?.settings?.setAppCachePath(context?.cacheDir?.path)
        web_view?.settings?.cacheMode = WebSettings.LOAD_DEFAULT

        web_view?.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                progress_bar?.hide()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progress_bar?.hide()
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
        if (!TextUtils.isEmpty(url) && web_view != null) {
            web_view?.loadUrl(url)
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
}// Required empty public constructor
