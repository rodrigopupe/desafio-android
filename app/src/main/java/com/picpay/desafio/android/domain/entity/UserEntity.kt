package com.picpay.desafio.android.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity (
    val photo: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable