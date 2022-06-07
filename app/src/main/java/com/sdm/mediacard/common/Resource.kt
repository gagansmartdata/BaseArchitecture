package com.sdm.mediacard.common

sealed class Resource<T>(val data: T? = null, val message: Any? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    //message is of Any type, it can be res int or string, so we can use them accordingly.
    class Error<T>(message: Any, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}