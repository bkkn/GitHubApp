package me.bkkn.githubapp.di

import me.bkkn.dil.Di
import me.bkkn.dil.Factory
import me.bkkn.dil.Singleton
import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DiModule() {
    private val baseUrl = GithubApi.githubUrl
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(GithubApi.githubUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    private val api: GithubApi by lazy { retrofit.create(GithubApi::class.java) }


    private val usersRepo: UsersRepo by lazy { RetrofitUsersRepoImpl(api) }
    private val randomString: String
        get() = UUID.randomUUID().toString()

    init {
        Di.add(UsersRepo::class, Singleton{usersRepo})
        Di.add(String::class, Factory{randomString})
    }
}
