package com.picpay.desafio.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.domain.entity.UserEntity
import com.picpay.desafio.android.utils.hide
import com.picpay.desafio.android.utils.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: UserEntity) {
            itemView.name.text = user.name
            itemView.username.text = user.username
            itemView.progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(user.photo)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.picture, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBar.hide()
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBar.show()
                    }
                })
        }
    }
}