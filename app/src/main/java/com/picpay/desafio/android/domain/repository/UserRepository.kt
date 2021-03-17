package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.model.UserResponse

interface UserRepository {

    suspend fun getUsers(): List<UserResponse>
}