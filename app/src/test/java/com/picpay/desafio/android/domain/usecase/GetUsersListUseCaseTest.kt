package com.picpay.desafio.android.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.core.BaseResource
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

class GetUsersListUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var getUsersListUseCase: GetUsersListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUsersListUseCase = GetUsersListUseCase(userRepository)
    }

    @Test
    fun `Should return a Success Resource when repository call is successfully`(): Unit = runBlocking {
        whenever(userRepository.getUsers()).thenReturn(fakeUserResponseList)
        val result = getUsersListUseCase.execute()
        assert(result is BaseResource.Success)
        assertEquals((result as BaseResource.Success).data, fakeUserEntityList)
        verify(userRepository).getUsers()
    }

    @Test
    fun `Should return a Failure when repository call throws and Exception`(): Unit = runBlocking {
        whenever(userRepository.getUsers()).thenThrow(fakeNotFoundHttpException)
        val result = getUsersListUseCase.execute()
        assert(result is BaseResource.Failure)
        assertEquals((result as BaseResource.Failure).exception, fakeNotFoundHttpException)
        verify(userRepository).getUsers()
    }
}