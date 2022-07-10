package me.bkkn.githubapp.di

import dagger.Component
import me.bkkn.githubapp.domain.repos.UsersRepo
import me.bkkn.githubapp.ui.users.MainActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
//    fun getUsersRepo(): UsersRepo
    fun inject(mainActivity: MainActivity)
}