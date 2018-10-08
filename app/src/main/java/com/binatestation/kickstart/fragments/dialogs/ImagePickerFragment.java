package com.binatestation.kickstart.fragments.dialogs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binatestation.kickstart.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Dialog fragment to pick image.
 */
public class ImagePickerFragment extends BaseDialogFragment implements View.OnClickListener {

    public static final String TAG = "ImagePickerFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private ImagePickerListener mImagePickerListener;

    public ImagePickerFragment() {
        // Required empty public constructor
    }

    /**
     * gets the instance of ImagePickerFragment
     *
     * @return ImagePickerFragment
     */
    public static ImagePickerFragment newInstance() {
        Log.d(TAG, "newInstance() called");
        Bundle args = new Bundle();

        ImagePickerFragment fragment = new ImagePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ImagePickerListener) {
            mImagePickerListener = (ImagePickerListener) context;
        }
    }

    /**
     * sets the ImagePickerListener
     *
     * @param imagePickerListener ImagePickerListener
     */
    public void setImagePickerListener(ImagePickerListener imagePickerListener) {
        this.mImagePickerListener = imagePickerListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mImagePickerListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View actionCameraView = view.findViewById(R.id.FIP_camera_layout);
        View actionGalleryView = view.findViewById(R.id.FIP_gallery_layout);
        View actionCancelView = view.findViewById(R.id.FIP_cancel);

        actionCameraView.setOnClickListener(this);
        actionGalleryView.setOnClickListener(this);
        actionCancelView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.FIP_camera_layout) {
            dispatchTakePictureIntent();
        } else if (id == R.id.FIP_gallery_layout) {
            dispatchPickFromGalleryIntent();
        } else {
            dismiss();
        }
    }

    /**
     * function for choosing image from gallery
     */
    private void dispatchPickFromGalleryIntent() {
        Intent pickFromGalleryIntent = new Intent();
        pickFromGalleryIntent.setType("image/*");
        pickFromGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        FragmentActivity activity = getActivity();
        if (activity != null && pickFromGalleryIntent.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(pickFromGalleryIntent, REQUEST_IMAGE_PICK);
        }
    }

    /**
     * function for capturing image
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap photo = (Bitmap) bundle.get("data");
                if (mImagePickerListener != null) {
                    mImagePickerListener.onImagePicked(photo);
                }
            }
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && getActivity() != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                if (mImagePickerListener != null) {
                    mImagePickerListener.onImagePicked(photo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dismiss();
    }

    public interface ImagePickerListener {
        void onImagePicked(Bitmap bitmap);
    }
}
