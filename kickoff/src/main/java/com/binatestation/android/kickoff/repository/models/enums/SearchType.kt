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
 * Last Updated at 7/4/20 4:21 PM.
 */

package com.binatestation.android.kickoff.repository.models.enums

enum class SearchType(val value: String) {
    EQUAL("_eq"),
    NOT_EQUAL("_not_eq"),

    // e.g. q[email_matches]=%@gmail.com
    MATCHES("_matches"),
    DOES_NOT_MATCH("_does_not_match"),
    MATCHES_ANY("_matches_any"),
    MATCHES_ALL("_matches_all"),
    DOES_NOT_MATCH_ANY("_does_not_match_any"),
    DOES_NOT_MATCH_ALL("_does_not_match_all"),
    LESS_THAN("_lt"),
    LESS_THAN_OR_EQUAL("_lteq"),
    GREATER_THAN("_gt"),
    GREATER_THAN_OR_EQUAL("_gteq"),

    // Only compatible with string columns. Example: q[name_present]=1 (SQL: col is not null AND col != "")
    NOT_NULL_AND_NOT_EMPTY("_present"),

    // (SQL: col is null OR col()"")
    IS_NULL_OR_EMPTY("_blank"),
    IS_NULL("_null"),
    IS_NOT_NULL("_not_null"),

    // e.g. q[name_in][]=Alice&q[name_in][]=Bob
    MATCH_ANY_VALUES_IN_ARRAY("_in"),
    MATCH_NONE_OF_VALUES_IN_ARRAY("_not_in"),

    // SQL: col < value1 OR col < value2
    LESS_THAN_ANY("_lt_any"),
    LESS_THAN_OR_EQUAL_TO_ANY("_lteq_any"),
    GREATER_THAN_ANY("_gt_any"),
    GREATER_THAN_OR_EQUAL_TO_ANY("_gteq_any"),

    // SQL: col < value1 AND col < value2
    LESS_THAN_ALL("_lt_all"),
    LESS_THAN_OR_EQUAL_TO_ALL("_lteq_all"),
    GREATER_THAN_ALL("_gt_all"),
    GREATER_THAN_OR_EQUAL_TO_ALL("_gteq_all"),
    NONE_OF_VALUES_IN_A_SET("_not_eq_all"),

    // SQL: col LIKE "value%"
    STARTS_WITH("_start"),
    DOES_NOT_START_WITH("_not_start"),
    STARTS_WITH_ANY_OF("_start_any"),
    STARTS_WITH_ALL_OF("_start_all"),
    DOES_NOT_START_WITH_ANY_OF("_not_start_any"),
    DOES_NOT_START_WITH_ALL_OF("_not_start_all"),

    // SQL: col LIKE "%value"
    ENDS_WITH("_end"),
    DOES_NOT_END_WITH("_not_end"),
    ENDS_WITH_ANY_OF("_end_any"),
    ENDS_WITH_ALL_OF("_end_all"),
    NOT_END_ANY("_not_end_any"),
    NOT_END_ALL("_not_end_all"),

    // uses LIKE
    CONTAINS_VALUE("_cont"),
    CONTAINS_ANY_OF("_cont_any"),
    CONTAINS_ALL_OF("_cont_all"),
    DOES_NOT_CONTAIN("_not_cont"),
    DOES_NOT_CONTAIN_ANY_OF("_not_cont_any"),
    DOES_NOT_CONTAIN_ALL_OF("_not_cont_all"),

    // uses LIKE
    CONTAINS_VALUE_WITH_CASE_INSENSITIVE("_i_cont"),
    CONTAINS_ANY_OF_VALUES_WITH_CASE_INSENSITIVE("_i_cont_any"),
    CONTAINS_ALL_OF_VALUES_WITH_CASE_INSENSITIVE("_i_cont_all"),
    DOES_NOT_CONTAINS_VALUE_WITH_CASE_INSENSITIVE("_not_i_cont"),
    DOES_NOT_CONTAINS_ANY_OF_VALUES_WITH_CASE_INSENSITIVE("_not_i_cont_any"),
    DOES_NOT_CONTAINS_ALL_OF_VALUES_WITH_CASE_INSENSITIVE("_not_i_cont_all"),
    IS_TRUE("_true"),
    IS_FALSE("_false")
}