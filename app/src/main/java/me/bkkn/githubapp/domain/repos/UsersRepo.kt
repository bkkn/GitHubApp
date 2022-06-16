package me.bkkn.githubapp.domain.repos

import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersRepo {

    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}