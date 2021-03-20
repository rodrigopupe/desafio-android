package com.picpay.desafio.android

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.presentation.MainViewModel
import com.picpay.desafio.android.utils.hide
import com.picpay.desafio.android.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListAdapter = UserListAdapter()
        binding.recyclerView.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        registerObservers()

        if (savedInstanceState == null) {
            viewModel.getUsersList()
        }
    }

    private fun registerObservers() {
        viewModel.usersListResponse.observe(this, usersListObserver)
    }

    private val usersListObserver = Observer<BaseResource<List<UserEntity>>> {
        binding.userListProgressBar.hide()
        when (it) {
            is BaseResource.Loading -> binding.userListProgressBar.show()
            is BaseResource.Success -> handleUsersList(it.data)
            is BaseResource.Failure -> showError(it.e)
        }
    }

    private fun handleUsersList(usersList: List<UserEntity>) {
        userListAdapter.users = usersList
    }

    private fun showError(e: Exception) {
        val message = getString(R.string.error)
        binding.recyclerView.hide()

        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}
