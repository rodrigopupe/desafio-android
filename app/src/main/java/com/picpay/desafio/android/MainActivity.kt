package com.picpay.desafio.android

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.core.BaseResource
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.presentation.MainViewModel
import com.picpay.desafio.android.utils.hide
import com.picpay.desafio.android.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        userListAdapter = UserListAdapter()
        recyclerView.apply {
            adapter = userListAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
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
        progressBar.hide()
        when (it) {
            is BaseResource.Loading -> progressBar.show()
            is BaseResource.Success -> handleUsersList(it.data)
            is BaseResource.Failure -> showError(it.e)
        }
    }

    private fun handleUsersList(usersList: List<UserEntity>) {
        userListAdapter.users = usersList
    }

    private fun showError(e: Exception) {
        val message = getString(R.string.error)
        recyclerView.hide()

        Log.e("ERROR", "Detalhes do erro: $e")

        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}
