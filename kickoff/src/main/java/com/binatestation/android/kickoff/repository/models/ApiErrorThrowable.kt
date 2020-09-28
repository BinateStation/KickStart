package com.binatestation.android.kickoff.repository.models

/**
 * ApiErrorThrowable
 *
 * @author Raghu Krishnan R
 */
data class ApiErrorThrowable(val msg: String) : Throwable(message = msg)