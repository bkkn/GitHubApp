package me.bkkn.githubapp

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import me.bkkn.githubapp.data.dummy.FakeUsersRepoImpl
import me.bkkn.githubapp.data.retrofit.GithubApi
import me.bkkn.githubapp.data.retrofit.RetrofitUsersRepoImpl
import me.bkkn.githubapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val baseUrl = GithubApi.githubUrl
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(GithubApi.githubUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    private val api: GithubApi by lazy { retrofit.create(GithubApi::class.java) }
    private val uiHandelr: Handler by lazy { Handler(Looper.getMainLooper()) }

    val usersRepo: UsersRepo by lazy { RetrofitUsersRepoImpl(api) }
//    val fakeUsersRepo: UsersRepo by lazy { FakeUsersRepoImpl(uiHandelr) }

    companion object Const {
        const val EXTRA_USER_KEY = "extra_user_key"
    }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App
