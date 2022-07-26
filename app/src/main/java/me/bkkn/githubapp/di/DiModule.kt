package me.bkkn.githubapp.di

import me.bkkn.dil.Module
import me.bkkn.dil.get
import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val appModule = Module() {
    val baseUrl = GithubApi.githubUrl
    singleton<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    singleton<GithubApi> { get<Retrofit>().create(GithubApi::class.java) }
    singleton<UsersRepo> { RetrofitUsersRepoImpl(get()) }
    factory { UUID.randomUUID().toString() }
}
