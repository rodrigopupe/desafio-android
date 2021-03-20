package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.entity.UserEntity

interface UserRepository {

    suspend fun getUsers(): List<UserEntity>
}