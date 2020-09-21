package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.databinding.FragmentImageBinding

private const val ARG_IMAGE_URL = "image_url"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(ARG_IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentImageBinding = DataBindingUtil.inflate<FragmentImageBinding>(
            inflater,
            R.layout.fragment_image,
            container,
            false
        )
        fragmentImageBinding.imageUrl = imageUrl
        return fragmentImageBinding.root
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
        fun newInstance(imageUrl: String?) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, imageUrl)
                }
            }
    }
}