package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

private const val ARG_IMAGE_URLS = "image_urls"

/**
 * A simple [Fragment] subclass. to show a list of images as Carousel.
 * Use the [ImageCarouselFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class ImageCarouselFragment : PageSliderWithIndicatorFragment() {

    private var imageUrls: ArrayList<String>? = null
    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP

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