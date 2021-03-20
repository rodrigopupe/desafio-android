package com.picpay.desafio.android.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.factory.fakeNotFoundHttpException
import com.picpay.desafio.android.factory.fakeUserEntityList
import com.picpay.desafio.android.factory.fakeUserResponseList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class UserRepositoryImplTest {

    @Mock
    private lateinit var service: PicPayService
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(service)
    }

    @Test
    fun `Should return a list of users`(): Unit = runBlocking {
        whenever(service.getUsers()).thenReturn(fakeUserResponseList)
        val list = userRepository.getUsers()
        assertEquals(list, fakeUserEntityList)
        verify(service).getUsers()
    }

    @Test(expected = HttpException::class)
    fun `Should throw an Exception when API has error`(): Unit = runBlocking {
        whenever(service.getUsers()).thenThrow(fakeNotFoundHttpException)
        userRepository.getUsers()
        verify(service).getUsers()
    }
}