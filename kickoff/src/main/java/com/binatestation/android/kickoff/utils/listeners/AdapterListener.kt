/*
 * Created By RKR
 * Last Updated at 14/12/19 5:32 PM.
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

package com.binatestation.android.kickoff.utils.listeners

/**
 * Created by RKR on 29-08-2018.
 * AdapterListener.
 */
interface AdapterListener {
    /**
     * Method to get click listener
     *
     * @return the click listener
     */
    val clickListener: OnListItemClickListener?

    /**
     * Method to get item at position
     *
     * @param position the position of item to get
     * @return the object at position
     */
    fun getItem(position: Int): Any


    /**
     * Secondary try adapter
     *
     * @return [com.binatestation.bms.models.enums.Type]
     */
    fun getType(): Int
}
