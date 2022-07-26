package me.bkkn.githubapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import me.bkkn.githubapp.domain.entities.UserEntity
import retrofit2.http.GET

interface GithubApi {
    companion object {
        const val githubUrl = "https://api.github.com/"
    }

    @GET("users")
    fun getUsers(): Single<List<UserEntity>>
}
