package com.picpay.desafio.android.di

import com.picpay.desafio.android.domain.usecase.GetUsersListUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUsersListUseCase(get()) }
}