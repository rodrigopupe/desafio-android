package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {
    override suspend fun getUsers(): List<UserResponse> {
        TODO("Not yet implemented")
    }
}