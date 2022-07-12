package me.bkkn.githubapp.ui.users

import io.reactivex.rxjava3.core.Observable
import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersContract {
    interface ViewModel {
        val usersObservable: Observable<List<UserEntity>>
        val errorObservable: Observable<Throwable>
        val progressObservable: Observable<Boolean>
        fun onRefresh()
        fun onUserDataRequested(id: Int): UserEntity
    }
}