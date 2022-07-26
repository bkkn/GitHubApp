package me.bkkn.githubapp.ui.profile

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import me.bkkn.dil.inject
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UserRepo
import me.bkkn.githubapp.domain.repos.UsersRepo

class ProfileViewModel {

    private val userRepo: UserRepo by inject("remote")

    val profileObservable: Observable<UserEntity> = BehaviorSubject.create()
    val errorObservable: Observable<Throwable> = BehaviorSubject.create()
    val progressObservable: Observable<Boolean> = BehaviorSubject.create()

    private fun loadData(id: Int) {
        progressObservable.mutable().onNext(true)
        userRepo.getUser(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressObservable.mutable().onNext(false)
                    profileObservable.mutable().onNext(it)
                },
                onError = {
                    progressObservable.mutable().onNext(false)
                    errorObservable.mutable().onNext(it)
                }
            )
    }

//    fun onUserDataRequested(id: Int): UserEntity {
//        return usersObservable.blockingFirst()[id]
//    }

    fun onLaunched(it: Int) {

    }

    private fun <T : Any> Observable<T>.mutable(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("Is is not Subject")
    }
}