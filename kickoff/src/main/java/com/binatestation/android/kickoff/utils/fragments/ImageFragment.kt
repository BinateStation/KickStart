/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.FragmentImageBinding

private const val ARG_IMAGE_URL = "image_url"
private const val ARG_SCALE_TYPE = "scale_type"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {

    private var imageUrl: String? = null
    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
    private lateinit var fragmentImageBinding: FragmentImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            imageUrl = bundle.getString(ARG_IMAGE_URL)
            bundle.getSerializable(ARG_SCALE_TYPE).takeIf { it is ImageView.ScaleType }?.apply {
                scaleType = this as ImageView.ScaleType
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentImageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image,
            container,
            false
        )
        fragmentImageBinding.imageUrl = imageUrl
        fragmentImageBinding.image.scaleType = scaleType
        return fragmentImageBinding.root
    }

    /**
     * to change the image scale type of the Image view
     * @param scaleType ScaleType
     */
    fun setScaleType(scaleType: ImageView.ScaleType) {
        this.scaleType = scaleType
        this.fragmentImageBinding.image.scaleType = scaleType
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param imageUrl Url of image to render.
         * @return A new instance of fragment ImageFragment.
         */
        @JvmStatic
        fun newInstance(
            imageUrl: String?,
            scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
        ) = ImageFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_IMAGE_URL, imageUrl)
                putSerializable(ARG_SCALE_TYPE, scaleType)
            }
        }
    }
}
