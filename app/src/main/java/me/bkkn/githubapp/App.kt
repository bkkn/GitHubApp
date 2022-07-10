package me.bkkn.githubapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import me.bkkn.githubapp.di.AppComponent
import me.bkkn.githubapp.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

    companion object Const {
        const val EXTRA_USER_KEY = "extra_user_key"
    }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App
