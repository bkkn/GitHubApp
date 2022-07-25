package me.bkkn.githubapp.di

import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import me.bkkn.githubapp.ui.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    val baseUrl = GithubApi.githubUrl

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    single<GithubApi> { get<Retrofit>().create(GithubApi::class.java) }
    single<UsersRepo> { RetrofitUsersRepoImpl(get()) }

    viewModel {
        UsersViewModel(get())
    }
}