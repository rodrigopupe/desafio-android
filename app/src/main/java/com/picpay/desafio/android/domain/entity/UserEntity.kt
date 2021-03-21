package com.picpay.desafio.android.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity (
    val photo: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable