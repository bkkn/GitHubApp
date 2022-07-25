package me.bkkn.githubapp.di

import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.reflect.KClass

interface Di {
    fun <T: Any> get(clazz: KClass<T>): T
}

class DiImpl : Di {
    override fun <T: Any> get(clazz: KClass<T>): T {
        return when (clazz){
            UsersRepo::class -> usersRepo as T
            String::class -> randomString as T
            else -> throw IllegalArgumentException("")
        }
    }

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


}