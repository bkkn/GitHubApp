package me.bkkn.githubapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.bkkn.githubapp.R
import me.bkkn.githubapp.databinding.ItemUserBinding
import me.bkkn.githubapp.domain.entities.UserEntity

class UserViewHolder(parent: ViewGroup, listener: UsersAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    ) {
    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }

    private val binding = ItemUserBinding.bind(itemView)

    fun bind(userEntity: UserEntity) {
        binding.mainActivityAvaterImageView.load(userEntity.avatarUrl)
        binding.mainActivityLoginTextView.text = userEntity.login
        binding.uidTextView.text = userEntity.id.toString()
    }
}