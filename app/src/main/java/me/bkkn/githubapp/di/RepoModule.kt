package me.bkkn.githubapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    private val baseUrl = GithubApi.githubUrl

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepo(api: GithubApi): UsersRepo {
        return RetrofitUsersRepoImpl(api)
    }
}