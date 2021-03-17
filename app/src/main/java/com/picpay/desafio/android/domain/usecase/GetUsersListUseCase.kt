package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsersListUseCase(private val repository: UserRepository) {

    suspend fun execute() : List<UserEntity> {
        return repository.getUsers().map {
            UserEntity(it.img, it.name, it.id, it.username)
        }
    }
}