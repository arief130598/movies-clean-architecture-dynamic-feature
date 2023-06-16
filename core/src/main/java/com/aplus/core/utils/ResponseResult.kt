package com.aplus.core.utils

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
    object Loading : ResponseResult<Nothing>()
}
