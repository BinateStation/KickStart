/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils.fragments.dialogs


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.binatestation.android.kickoff.repository.models.ErrorModel

/**
 * Alert dialog fragment
 */
class AlertDialogFragment : BaseDialogFragment() {

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mPositiveButtonText: String? = null
    private var mNegativeButtonText: String? = null
    private var mNeutralButtonText: String? = null

    private var mOnClickListener: DialogInterface.OnClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (mOnClickListener == null) {
            if (context is DialogInterface.OnClickListener) {
                mOnClickListener = context
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mOnClickListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            with(it) {
                mTitle = getString(KEY_TITLE)
                mMessage = getString(KEY_MESSAGE)
                if (TextUtils.isEmpty(mPositiveButtonText) && containsKey(KEY_POSITIVE_BUTTON_TEXT)) {
                    mPositiveButtonText = getString(KEY_POSITIVE_BUTTON_TEXT)
                }
                if (TextUtils.isEmpty(mNegativeButtonText) && containsKey(KEY_NEGATIVE_BUTTON_TEXT)) {
                    mNegativeButtonText = getString(KEY_NEGATIVE_BUTTON_TEXT)
                }
                if (TextUtils.isEmpty(mNeutralButtonText) && containsKey(KEY_NEUTRAL_BUTTON_TEXT)) {
                    mNeutralButtonText = getString(KEY_NEUTRAL_BUTTON_TEXT)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            val dialog = super.onCreateDialog(savedInstanceState)
            // request a window without the title
            val window = dialog.window
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog
        } else {
            with(AlertDialog.Builder(requireContext())) {
                setTitle(mTitle); @Suppress("DEPRECATION")
            setMessage(Html.fromHtml(mMessage)); setPositiveButton(
                mPositiveButtonText,
                mOnClickListener
            ); setNegativeButton(mNegativeButtonText, mOnClickListener); setNeutralButton(
                mNeutralButtonText,
                mOnClickListener
            ); create()
            }
        }
    }


    @Suppress("unused")
    fun setOnClickListener(onClickListener: DialogInterface.OnClickListener) {
        this.mOnClickListener = onClickListener
    }

    @Suppress("unused")
    fun setPositiveText(positiveButtonText: String) {
        this.mPositiveButtonText = positiveButtonText
    }

    @Suppress("unused")
    fun setNegativeText(negativeButtonText: String) {
        this.mNegativeButtonText = negativeButtonText
    }

    companion object {
        private const val TAG = "AlertDialogFragment"
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_POSITIVE_BUTTON_TEXT = "positive_button_text"
        private const val KEY_NEGATIVE_BUTTON_TEXT = "negative_button_text"
        private const val KEY_NEUTRAL_BUTTON_TEXT = "neutral_button_text"

        fun newInstance(errorModel: ErrorModel): AlertDialogFragment {
            Log.d(TAG, "newInstance() called with: errorModel = [$errorModel]")
            val args = Bundle()
            args.putString(KEY_TITLE, errorModel.errorTitle)
            args.putString(KEY_MESSAGE, errorModel.errorMessage)
            val fragment = AlertDialogFragment()
            fragment.arguments = args
            return fragment
        }

        /**
         * gets the instance of AlertDialogFragment
         *
         * @param title   String
         * @param message String
         * @return AlertDialogFragment
         */
        fun newInstance(title: String, message: String): AlertDialogFragment {
            Log.d(TAG, "newInstance() called with: title = [$title], message = [$message]")
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_MESSAGE, message)
            val fragment = AlertDialogFragment()
            fragment.arguments = args
            return fragment
        }

        /**
         * gets the instance of AlertDialogFragment
         *
         * @param title              String value
         * @param message            String value
         * @param positiveButtonText String value
         * @return AlertDialogFragment
         */
        fun newInstance(
            title: String?,
            message: String?,
            positiveButtonText: String
        ): AlertDialogFragment {
            Log.d(
                TAG,
                "newInstance() called with: title = [$title], message = [$message], positiveButtonText = [$positiveButtonText]"
            )
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_MESSAGE, message)
            args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText)
            val fragment = AlertDialogFragment()
            fragment.arguments = args
            return fragment
        }

        /**
         * gets the instance of AlertDialogFragment
         *
         * @param title              String value
         * @param message            String value
         * @param positiveButtonText String value
         * @param negativeButtonText String value
         * @return AlertDialogFragment
         */
        fun newInstance(
            title: String,
            message: String,
            positiveButtonText: String,
            negativeButtonText: String
        ): AlertDialogFragment {
            Log.d(
                TAG,
                "newInstance() called with: title = [$title], message = [$message], positiveButtonText = [$positiveButtonText], negativeButtonText = [$negativeButtonText]"
            )
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_MESSAGE, message)
            args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText)
            args.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText)
            val fragment = AlertDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
