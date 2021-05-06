/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils.fragments.dialogs


import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binatestation.android.kickoff.R
import java.io.IOException

/**
 * Dialog fragment to pick image.
 */
class ImagePickerFragment : BaseDialogFragment() {
    private var mImagePickerListener: ImagePickerListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ImagePickerListener) {
            mImagePickerListener = context
        }
    }

    @Suppress("unused")
            /**
             * sets the ImagePickerListener
             *
             * @param imagePickerListener ImagePickerListener
             */
    fun setImagePickerListener(imagePickerListener: ImagePickerListener) {
        this.mImagePickerListener = imagePickerListener
    }

    override fun onDetach() {
        super.onDetach()
        mImagePickerListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.FIP_camera_layout)
            .setOnClickListener { dispatchTakePictureIntent() }
        view.findViewById<View>(R.id.FIP_gallery_layout)
            .setOnClickListener { dispatchPickFromGalleryIntent() }
        view.findViewById<View>(R.id.FIP_cancel).setOnClickListener { dismiss() }
    }

    /**
     * function for choosing image from gallery
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchPickFromGalleryIntent() {
        val pickFromGalleryIntent = Intent()
        pickFromGalleryIntent.type = "image/*"
        pickFromGalleryIntent.action = Intent.ACTION_GET_CONTENT
        val activity = activity
        if (activity != null && pickFromGalleryIntent.resolveActivity(activity.packageManager) != null) {
            startActivityForResult(pickFromGalleryIntent, REQUEST_IMAGE_PICK)
        }
    }

    /**
     * function for capturing image
     */
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(
            TAG,
            "onActivityResult() called with: requestCode = [$requestCode], resultCode = [$resultCode], data = [$data]"
        )
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            val bundle = data.extras
            if (bundle != null) {
                val photo = bundle.get("data") as Bitmap
                if (mImagePickerListener != null) {
                    mImagePickerListener!!.onImagePicked(photo)
                }
            }
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && activity != null) {
            val selectedImage = data!!.data
            try {
                @Suppress("DEPRECATION") val photo =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImage)
                if (mImagePickerListener != null) {
                    mImagePickerListener!!.onImagePicked(photo)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        dismiss()
    }

    interface ImagePickerListener {
        fun onImagePicked(bitmap: Bitmap)
    }

    companion object {

        const val TAG = "ImagePickerFragment"

        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2

        /**
         * gets the instance of ImagePickerFragment
         *
         * @return ImagePickerFragment
         */
        fun newInstance(): ImagePickerFragment {
            Log.d(TAG, "newInstance() called")
            val args = Bundle()

            val fragment = ImagePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
