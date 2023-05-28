package com.aplus.core.extensions

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi


@Throws(JsonDataException::class)
inline fun <reified T> T.serialize(): String {
    val jsonAdapter = Moshi.Builder().build().adapter(T::class.java)
    return jsonAdapter.toJson(this)
}

@Throws(JsonDataException::class)
inline fun <reified T> String.deserialize(): T? {
    val jsonAdapter = Moshi.Builder().build().adapter(T::class.java)
    return jsonAdapter.fromJson(this)
}
