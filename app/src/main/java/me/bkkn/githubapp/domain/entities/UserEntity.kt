package me.bkkn.githubapp.domain.entities

import com.google.gson.annotations.SerializedName

data class UserEntity(
    val login: String = "",
    val id: Long = -1,
    @field:SerializedName("avatar_url")
    val avatarUrl: String = ""
)

