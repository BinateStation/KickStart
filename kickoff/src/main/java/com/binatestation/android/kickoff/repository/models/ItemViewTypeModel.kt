/*
 * Created By RKR
 * Last Updated at 15/12/19 12:08 AM.
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

package com.binatestation.android.kickoff.repository.models

data class ItemViewTypeModel<T, VH, VDB>(
    var clsType: Class<T>? = null,
    var viewHolder: Class<VH>? = null,
    var layoutId: Int = 0,
    var viewDataBindingType: Class<VDB>? = null
)