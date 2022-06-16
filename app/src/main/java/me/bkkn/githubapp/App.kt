package me.bkkn.githubapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import me.bkkn.githubapp.data.FakeUsersRepoImpl

class App : Application() {
    val usersRepo by lazy { FakeUsersRepoImpl() }

    override fun onCreate() {
        super.onCreate()
    }

}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App
