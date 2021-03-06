/*
 * Created By RKR
 * Last Updated at 2/1/20 1:13 PM.
 *
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
 */

package com.binatestation.kickstart.repository.models

import com.binatestation.android.kickoff.repository.models.LoginModel

/**
 * Created by RKR on 24-06-2019.
 *
 * ApiRequest
 */

data class ApiRequest(
    var loginModel: LoginModel? = null
)