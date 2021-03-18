package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.domain.usecase.GetUsersListUseCase
import kotlinx.coroutines.*

class MainViewModel(
    private val getUsersListUseCase: GetUsersListUseCase
) : ViewModel() {

    private val _usersListLiveData = MutableLiveData<BaseResource<List<UserEntity>>>()
    val usersListResponse: LiveData<BaseResource<List<UserEntity>>>
        get() = _usersListLiveData

    fun getUsersList() {
        viewModelScope.launch {
            _usersListLiveData.value = BaseResource.Loading
            withContext(Dispatchers.IO) {
                val result = getUsersListUseCase.execute()
                _usersListLiveData.postValue(result)
            }
        }
    }
}