package com.picpay.desafio.android.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoCountingIdlingResource {

    private const val RESOURCE_ALIAS = "GLOBAL"

    @JvmStatic
    val countingIdlingResource = CountingIdlingResource(RESOURCE_ALIAS)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}