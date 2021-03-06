package me.bkkn.githubapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment

import me.bkkn.githubapp.di.appModule

class App : Application() {
  init {
      appModule.install()
  }

  companion object Const {
      const val EXTRA_USER_KEY = "extra_user_key"
  }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App
