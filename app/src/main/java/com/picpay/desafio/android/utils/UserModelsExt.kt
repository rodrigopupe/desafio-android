package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.entity.UserEntity

fun UserResponse.toUserEntity() = UserEntity(this.img, this.name, this.id, this.username)