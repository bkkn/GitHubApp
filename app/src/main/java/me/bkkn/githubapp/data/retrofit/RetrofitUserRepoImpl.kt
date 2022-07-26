package me.bkkn.githubapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UserRepo

class RetrofitUserRepoImpl(
    private val api: GithubApi
) : UserRepo {
    override fun getUser(
        id: Int,
        onSuccess: (UserEntity) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        api.getUser(id).subscribeBy (
            onSuccess = {onSuccess.invoke(it)},
            onError = {onError?.invoke(it)}
        )
    }

    override fun getUser(id : Int): Single<UserEntity> = api.getUser(id)
}