package me.bkkn.githubapp.data.retrofit

import me.bkkn.githubapp.domain.entities.UserEntity
import retrofit2.Call
import retrofit2.http.GET

interface GithubApi {

    companion object{
        const val githubUrl = "https://api.github.com/"
    }

    @GET("users")
    fun getUsers(): Call<List<UserEntity>>
}
