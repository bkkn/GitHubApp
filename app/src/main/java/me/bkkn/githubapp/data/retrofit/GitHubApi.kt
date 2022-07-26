package me.bkkn.githubapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import me.bkkn.githubapp.domain.entities.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    companion object {
        const val githubUrl = "https://api.github.com/"
    }

    @GET("users")
    fun getUsers(): Single<List<UserEntity>>

    @GET("users")
    fun getUser(@Query("id") id: Int?): Single<UserEntity>
}
