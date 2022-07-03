package me.bkkn.githubapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUsersRepoImpl : UsersRepo {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GithubApi.githubUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val api: GithubApi = retrofit.create(GithubApi::class.java)

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        api.getUsers().subscribeBy(
            onSuccess = { onSuccess.invoke(it) },
            onError = { onError?.invoke(it) }
        )
    }

    override fun getUsers(): Single<List<UserEntity>> = api.getUsers()
}