/*
 * Created By RKR
 * Last Updated at 14/12/19 3:40 PM.
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

package com.binatestation.kickstart.utils

import androidx.databinding.InverseMethod
import java.util.*

@Suppress("unused")
object Converter {
    @JvmStatic
    fun dateToString(value: Long): String {
        return Utils.displayDate(value)
    }

    @JvmStatic
    @InverseMethod("stringToAmount")
    fun amountToString(value: Double?): String? {
        return value?.let { String.format(Locale.getDefault(), "%.2f", it) }
    }

    @JvmStatic
    fun stringToAmount(value: String?): Double? {
        return value?.let { Utils.ensureDouble(it) }
    }

    @JvmStatic
    fun amountToStringWithRupeeSymbol(value: Double): String {
        return String.format(Locale.getDefault(), "\u20B9%.2f", value)
    }

    @JvmStatic
    @InverseMethod("stringToQty")
    fun qtyToString(value: Double?): String? {
        return value?.let {
            if (value % 1 == 0.0)
                String.format(Locale.getDefault(), "%.0f", value)
            else
                String.format(Locale.getDefault(), "%.3f", value)
        }
    }

    @JvmStatic
    fun stringToQty(value: String?): Double? {
        return value?.let { Utils.ensureDouble(it) }
    }

    fun calcBalance(value: Double, receivedAmount: String?): String {
        val amount: Double = try {
            receivedAmount?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
        return String.format(Locale.getDefault(), "%.2f", value - amount)
    }


    @JvmStatic
    @InverseMethod("stringToCount")
    fun countToString(value: Int?): String? {
        return value?.toString()
    }

    @JvmStatic
    fun stringToCount(value: String?): Int? {
        return value?.let { Utils.ensureInteger(it) }
    }


}