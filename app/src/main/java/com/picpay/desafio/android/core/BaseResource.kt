package com.picpay.desafio.android.core

sealed class BaseResource<out T> {
    object Loading : BaseResource<Nothing>()
    data class Success<out T>(val data: T) : BaseResource<T>()
    data class Failure(val e: Exception) : BaseResource<Nothing>()
}