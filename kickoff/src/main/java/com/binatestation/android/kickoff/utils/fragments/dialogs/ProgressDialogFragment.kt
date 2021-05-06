/*
 * (c) Binate Station Private Limited. All rights reserved.
 */
package com.binatestation.android.kickoff.utils.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import com.binatestation.android.kickoff.R

/**
 * Dialog fragment to show progress dialog.
 */
class ProgressDialogFragment : BaseDialogFragment() {

    private var showing: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val progressBar = ProgressBar(context)
        val layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        progressBar.layoutParams = layoutParams
        return progressBar
    }


    override fun show(manager: FragmentManager, tag: String?) {
        if (!showing) {
            super.show(manager, tag)
            showing = true
        }
    }

    override fun dismiss() {
        if (showing) {
            super.dismiss()
            showing = false
        }
    }

    companion object {

        const val TAG = "ProgressDialogFragment"

        private var sProgressDialogFragment: ProgressDialogFragment? = null

        val instance: ProgressDialogFragment?
            get() {
                if (sProgressDialogFragment == null) {
                    sProgressDialogFragment = newInstance()
                }
                return sProgressDialogFragment
            }

        /**
         * gets the instance of ProgressDialogFragment
         *
         * @return ProgressDialogFragment
         */
        fun newInstance(): ProgressDialogFragment {

            val args = Bundle()

            val fragment = ProgressDialogFragment()
            fragment.arguments = args
            fragment.setStyle(STYLE_NO_TITLE, R.style.AppDialogTheme_Translucent)
            fragment.isCancelable = false
            return fragment
        }
    }
}// Required empty public constructor
