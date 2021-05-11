/*
 * (c) Binate Station Private Limited. All rights reserved.
 */
@file:Suppress("unused")

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

private const val ARG_IMAGE_URLS = "image_urls"

/**
 * A simple [Fragment] subclass. to show a list of images as Carousel.
 * Use the [ImageCarouselFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class ImageCarouselFragment : PageSliderWithIndicatorFragment() {

    private var imageUrls: ArrayList<String>? = null
    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
    private var currentPage = 0

    /**
     * Create ticker channel with required delay
     */
    @ObsoleteCoroutinesApi
    private val tickerChannel = ticker(delayMillis = 3000, initialDelayMillis = 0)

    /**
     * Create runnable
     */
    private val update = Runnable {
        if (currentPage == getCount()) {
            currentPage = 0
        }
        viewPager?.setCurrentItem(currentPage++, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrls = it.getStringArrayList(ARG_IMAGE_URLS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUrls?.let { setImages(it) }
    }

    fun setImages(imageUrls: List<String>) {
        imageUrls.forEach {
            addFragment(ImageFragment.newInstance(it, scaleType))
        }
    }

    /**
     * Function for start auto scroll
     */
    @ObsoleteCoroutinesApi
    fun startAutoScroll() {
        lifecycleScope.launch {
            for (event in tickerChannel) {
                withContext(Dispatchers.Main) {
                    update.run()
                }
            }
        }
    }

    /**
     * Function for stop auto scroll
     */
    @ObsoleteCoroutinesApi
    fun stopAutoScroll() {
        tickerChannel.cancel()
    }

    /**
     * to change the image scale type of the Image view
     * @param scaleType ScaleType
     */
    fun setScaleType(scaleType: ImageView.ScaleType) {
        this.scaleType = scaleType
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param imageUrls List image Urls to show in Carousel.

         * @return A new instance of fragment ImageCarouselFragment.
         */
        @JvmStatic
        fun newInstance(imageUrls: ArrayList<String>) =
            ImageCarouselFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_IMAGE_URLS, imageUrls)
                }
            }
    }
}
