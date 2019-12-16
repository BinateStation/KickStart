/*
 * Created By RKR
 * Last Updated at 14/12/19 5:23 PM.
 *
 * Copyright (c) 2019. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    ): View? {
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
