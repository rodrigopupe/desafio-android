package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val service: PicPayService) : UserRepository {
    override suspend fun getUsers(): List<UserResponse> = withContext(Dispatchers.IO) {
        return@withContext service.getUsers()
    }
}