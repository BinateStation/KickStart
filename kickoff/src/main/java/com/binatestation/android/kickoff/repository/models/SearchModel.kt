/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.repository.models

import com.binatestation.android.kickoff.repository.models.enums.SearchType

data class SearchModel(
    var query: String,
    var columnMap: HashMap<SearchType, String>
)
