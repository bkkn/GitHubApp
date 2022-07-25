package me.bkkn.githubapp.domain.repos

import io.reactivex.rxjava3.core.Single
import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersRepo {
    //Callback based
    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )

    //non Callback based
    fun getUsers(): Single<List<UserEntity>>
}