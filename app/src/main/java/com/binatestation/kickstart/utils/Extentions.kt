/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last Updated at 7/4/20 7:35 PM.
 */

@file:Suppress("unused")

package com.binatestation.kickstart.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.binatestation.android.kickoff.repository.models.ErrorModel
import com.binatestation.android.kickoff.utils.Constants
import com.binatestation.android.kickoff.utils.fragments.dialogs.AlertDialogFragment
import com.binatestation.android.kickoff.utils.fragments.dialogs.ProgressDialogFragment
import com.binatestation.kickstart.R
import org.json.JSONObject
import java.util.*


/**
 * sets the title
 *
 * @param title String value
 */
fun AppCompatActivity.setSupportActionBarTitle(title: String) {
    supportActionBar?.let {
        with(supportActionBar!!) {
            setDisplayShowTitleEnabled(true)
            setLogo(null)
            this.title = title
        }
    }
}

@Suppress("unused")
fun AppCompatActivity.hideHomeAsUp() {
    supportActionBar?.let {
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(false)
        }
    }
}


@Suppress("unused")
        /**
         * show alert message
         *
         * @param message String value
         * @return instance of AlertDialogFragment
         */
fun FragmentActivity.showAlert(message: String): AlertDialogFragment {
    return showAlert("", message)
}

@Suppress("MemberVisibilityCanBePrivate")
        /**
         * show alert message
         *
         * @param title   String value
         * @param message String value
         * @return instance of AlertDialogFragment
         */
fun FragmentActivity.showAlert(title: String?, message: String?): AlertDialogFragment {
    val alertDialogFragment =
        AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes))
    try {
        alertDialogFragment.show(supportFragmentManager, alertDialogFragment.tag)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return alertDialogFragment
}

@Suppress("unused")
        /**
         * show alert
         *
         * @param errorModel ErrorModel
         */
fun FragmentActivity.showAlert(errorModel: ErrorModel): AlertDialogFragment {
    return if (errorModel.show) {
        showAlert(errorModel.errorTitle, errorModel.errorMessage)
    } else {
        AlertDialogFragment()
    }
}


@Suppress("unused")
        /**
         * show alert
         *
         * @param jsonObject ErrorModel
         */
fun FragmentActivity.showAlert(jsonObject: JSONObject): AlertDialogFragment {
    return showAlert(
        jsonObject.optString(Constants.GeneralConstants.KEY_MESSAGE_TITLE), jsonObject.optString(
            Constants.GeneralConstants.KEY_MESSAGE
        )
    )
}


/**
 * show progress wheel
 */
fun FragmentActivity.showProgressWheel() {
    ProgressDialogFragment.instance?.show(supportFragmentManager, ProgressDialogFragment.TAG)
}

/**
 * hide progress wheel
 */
@Suppress("unused")
fun hideProgressWheel() {
    ProgressDialogFragment.instance?.dismiss()
}

@Suppress("unused")
        /**
         * show progress
         *
         * @param swipeRefreshLayout SwipeRefreshLayout
         */
fun showProgress(swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout?) {
    if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
    }
}

@Suppress("unused")
        /**
         * hide progress
         *
         * @param swipeRefreshLayout SwipeRefreshLayout
         */
fun hideProgress(swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout?) {
    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing) {
        swipeRefreshLayout.isRefreshing = false
    }
}


fun setColorSchemeResources(swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout) {
    swipeRefreshLayout.setColorSchemeResources(
        R.color.progress_red,
        R.color.progress_blue,
        R.color.progress_yellow,
        R.color.progress_green
    )
}


@Suppress("unused")
        /**
         * show alert message
         *
         * @param message String value
         * @return instance of AlertDialogFragment
         */
fun Fragment.showAlert(message: String): AlertDialogFragment? {
    return showAlert("", message)
}

@Suppress("MemberVisibilityCanBePrivate")
        /**
         * show alert message
         *
         * @param title   String value
         * @param message String value
         * @return instance of AlertDialogFragment
         */
fun Fragment.showAlert(title: String?, message: String?): AlertDialogFragment? {
    if (context == null) return null
    val alertDialogFragment =
        AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes))
    try {
        alertDialogFragment.show(childFragmentManager, alertDialogFragment.tag)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return alertDialogFragment
}

@Suppress("unused")
        /**
         * show alert
         *
         * @param errorModel ErrorModel
         */
fun Fragment.showAlert(errorModel: ErrorModel): AlertDialogFragment? {
    return if (errorModel.show) {
        showAlert(errorModel.errorTitle, errorModel.errorMessage)
    } else {
        AlertDialogFragment()
    }
}


@Suppress("unused")
        /**
         * show alert
         *
         * @param jsonObject ErrorModel
         */
fun Fragment.showAlert(jsonObject: JSONObject): AlertDialogFragment? {
    return showAlert(
        jsonObject.optString(Constants.GeneralConstants.KEY_MESSAGE_TITLE), jsonObject.optString(
            Constants.GeneralConstants.KEY_MESSAGE
        )
    )
}

/**
 * sets the title
 *
 * @param title String value
 */
fun Fragment.setTitle(title: String) {
    try {
        if (activity is AppCompatActivity) {
            val baseActivity = activity as AppCompatActivity?
            baseActivity?.setSupportActionBarTitle(title)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * show progress wheel
 */
@Suppress("unused")
fun Fragment.showProgressWheel() {
    val fragmentActivity = activity
    if (fragmentActivity is AppCompatActivity) {
        val baseActivity = fragmentActivity as AppCompatActivity?
        baseActivity?.showProgressWheel()
    }
}

fun String.makeLine(count: Int): String {
    var line = ""
    while (line.length < count) {
        line += this
    }
    return line
}

fun Date.atEndOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

fun Date.atStartOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun Date.aWeekBefore(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.WEEK_OF_MONTH, -1)
    return calendar.time
}

fun Date.aMonthBefore(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, -1)
    return calendar.time
}

fun Date.aYearBefore(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.YEAR, -1)
    return calendar.time
}