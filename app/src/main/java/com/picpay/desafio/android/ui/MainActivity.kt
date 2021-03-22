package com.picpay.desafio.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.presentation.MainViewModel
import com.picpay.desafio.android.utils.EspressoCountingIdlingResource
import com.picpay.desafio.android.utils.hide
import com.picpay.desafio.android.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    private val userListAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        registerObservers()

        if (savedInstanceState == null) {
            getData()
        }
    }

    private fun setupView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
        }
    }

    private fun getData() {
        EspressoCountingIdlingResource.increment()
        viewModel.getUsersList()
    }

    private fun registerObservers() {
        viewModel.usersListResponse.observe(this, usersListObserver)
    }

    private val usersListObserver = Observer<BaseResource<List<UserEntity>>> {
        binding.userListProgressBar.hide()
        when (it) {
            is BaseResource.Loading -> binding.userListProgressBar.show()
            is BaseResource.Success -> handleUsersList(it.data)
            is BaseResource.Failure -> showError()
        }
    }

    private fun handleUsersList(usersList: List<UserEntity>) {
        userListAdapter.users = usersList
        EspressoCountingIdlingResource.decrement()
    }

    private fun showError() {
        binding.recyclerView.hide()

        val message = getString(R.string.error)

        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}
