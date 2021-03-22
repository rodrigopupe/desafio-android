package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.utils.toUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val service: PicPayService) : UserRepository {
    override suspend fun getUsers(): List<UserEntity> = withContext(Dispatchers.IO) {
        return@withContext service.getUsers().map { it.toUserEntity() }
    }
}