package me.bkkn.githubapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit

class RetrofitUsersRepoImpl(
    private val api: GithubApi
) : UsersRepo {
    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        api.getUsers().subscribeBy(
            onSuccess = { onSuccess.invoke(it) },
            onError = { onError?.invoke(it) }
        )
    }

    override fun getUsers(): Single<List<UserEntity>> = api.getUsers()
}