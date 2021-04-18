package com.hjcho.itunes_search.domain


sealed class Result<out T> {

    data class Success<out T>(val data: T, val cached: Boolean = false) : Result<T>()

    data class Error(val msg: ErrorMessage) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$msg]"
        }
    }
}

interface ErrorMessage

class IllegalResponseException(msg: String) : RuntimeException(msg)

