package com.picpay.desafio.android.factory

import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.utils.toUserEntity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

val fakeUserResponseList = listOf(
    UserResponse("https://randomuser.me/api/portraits/men/67.jpg", "Fulano de Tal", 1234, "@fulanodetal"),
    UserResponse("https://randomuser.me/api/portraits/women/34.jpg", "Ciclana de Tal", 4321, "@ciclanadetal")
)

val fakeNotFoundHttpException = HttpException(
    Response.error<String>(
        404, "HTTP 404 Not Found".toResponseBody("text/plain".toMediaType())
    )
)

val fakeUserEntityList = fakeUserResponseList.map { it.toUserEntity() }

val fakeBaseResourceSuccess = BaseResource.Success(fakeUserEntityList)
val fakeBaseResourceFailure = BaseResource.Failure(fakeNotFoundHttpException)