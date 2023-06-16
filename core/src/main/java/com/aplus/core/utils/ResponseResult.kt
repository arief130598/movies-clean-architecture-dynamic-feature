package com.aplus.core.utils

import retrofit2.Response

sealed class ResponseResult<out R> {
    object Loading : ResponseResult<Nothing>()
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
}

fun <T> Response<T>.validateResponse(): ResponseResult<T> {
    return try {
        if(body() != null) ResponseResult.Success(body()!!)
        else ResponseResult.Error(Throwable(message()))
    } catch (throwable: Throwable) {
        ResponseResult.Error(throwable)
    }
}
