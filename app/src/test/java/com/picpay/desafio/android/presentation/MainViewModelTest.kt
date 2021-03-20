package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.GetUsersListUseCase
import com.picpay.desafio.android.factory.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var getUsersListUseCase: GetUsersListUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var observer: Observer<BaseResource<List<UserEntity>>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<BaseResource<List<UserEntity>>>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        getUsersListUseCase = GetUsersListUseCase(userRepository)
        mainViewModel = MainViewModel(getUsersListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Should post a success data`(): Unit = runBlocking {
        whenever(userRepository.getUsers()).thenReturn(fakeUserEntityList)

        mainViewModel.usersListResponse.observeForever(observer)
        mainViewModel.getUsersList()

        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        val states = argumentCaptor.allValues
        assert(states.first() is BaseResource.Loading)
        assert(states.last() is BaseResource.Success)
        assertEquals((states.last() as BaseResource.Success), fakeBaseResourceSuccess)
        verify(userRepository).getUsers()
    }

    @Test
    fun `Should post a failure data`(): Unit = runBlocking {
        whenever(userRepository.getUsers()).thenThrow(fakeNotFoundHttpException)

        mainViewModel.usersListResponse.observeForever(observer)
        mainViewModel.getUsersList()

        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        val states = argumentCaptor.allValues
        assert(states.first() is BaseResource.Loading)
        assert(states.last() is BaseResource.Failure)
        assertEquals((states.last() as BaseResource.Failure), fakeBaseResourceFailure)
        verify(userRepository).getUsers()
    }
}