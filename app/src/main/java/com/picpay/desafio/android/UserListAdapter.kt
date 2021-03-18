package com.picpay.desafio.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.utils.hide
import com.picpay.desafio.android.utils.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users = emptyList<UserEntity>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUserBinding.inflate(inflater, parent, false)

        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserListItemViewHolder(private val itemBinding: ListItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(user: UserEntity) {
            itemBinding.name.text = user.name
            itemBinding.username.text = user.username
            itemBinding.progressBar.show()
            Picasso.get()
                .load(user.photo)
                .error(R.drawable.ic_round_account_circle)
                .into(itemBinding.picture, object : Callback {
                    override fun onSuccess() {
                        itemBinding.progressBar.hide()
                    }

                    override fun onError(e: Exception?) {
                        itemBinding.progressBar.show()
                    }
                })
        }
    }
}