package me.bkkn.githubapp.domain

import me.bkkn.githubapp.domain.UserEntity

interface UsersRepo {

    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}