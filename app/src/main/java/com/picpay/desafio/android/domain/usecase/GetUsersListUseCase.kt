package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsersListUseCase(private val repository: UserRepository) {

    suspend fun execute(): BaseResource<List<UserEntity>> {
        return try {
            val users = repository.getUsers()
            BaseResource.Success(users)
        } catch (e: Exception) {
            BaseResource.Failure(e)
        }
    }
}