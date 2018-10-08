package com.binatestation.kickstart.fragments.dialogs;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.binatestation.kickstart.R;


/**
 * A dialog fragment to show the given url in web view.
 */
public class WebViewDialogFragment extends BaseDialogFragment {

    public static final String TAG = "WebViewDialogFragment";

    private static final String KEY_URL = "URL";

    private String url;
    private ContentLoadingProgressBar progressBar;
    private WebView webView;
    private String mTitle;

    public WebViewDialogFragment() {
        // Required empty public constructor
    }

    /**
     * gets the instance of WebViewDialogFragment
     *
     * @param url String
     * @return WebViewDialogFragment
     */
    public static WebViewDialogFragment newInstance(String url) {
        Log.d(TAG, "newInstance() called with: url = [" + url + "]");
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        WebViewDialogFragment fragment = new WebViewDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_URL)) {
            url = bundle.getString(KEY_URL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view_dialog, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.web_view);
        progressBar = view.findViewById(R.id.progress_bar);

        webView.getSettings().setJavaScriptEnabled(true);        // enable javascript just test
        webView.getSettings().setAppCacheEnabled(true);
        if (getContext() != null) {
            webView.getSettings().setAppCachePath(getContext().getCacheDir().getPath());
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.hide();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.hide();
            }
        });

        loadUrl();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mTitle)) {
            try {
                if (getActivity() != null) {
                    getActivity().setTitle(mTitle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * loads the web url
     */
    private void loadUrl() {
        if (!TextUtils.isEmpty(url) && webView != null) {
            webView.loadUrl(url);
        }
    }

    /**
     * sets the web url
     *
     * @param url url to load
     */
    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
        loadUrl();
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    public void setTitle(String title) {
        mTitle = title;
    }
}
