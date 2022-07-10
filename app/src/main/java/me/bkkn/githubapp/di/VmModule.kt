package me.bkkn.githubapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.bkkn.githubapp.domain.repos.UsersRepo
import me.bkkn.githubapp.ui.users.UsersViewModel
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class VmModule {
    @Provides
    fun provideUsersViewModel(
        usersRepo: UsersRepo
    ): UsersViewModel {
        return UsersViewModel(usersRepo)
    }
}