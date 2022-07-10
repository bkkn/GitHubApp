package me.bkkn.githubapp.ui.users

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersContract {
    // contracts for view-presenter interaction

//    interface View{
//        // do minimal things needed for View
////        fun showUsers(users: List<UserEntity>)
////        fun showError(throwable: Throwable)
////        fun showProgress(inProgress: Boolean)
////        fun showProfile(id: Int)
//
//    }

    interface ViewModel{
        // logic is here
        val usersObservable : Observable<List<UserEntity>>
        val errorObservable : Observable<Throwable>
        val progressObservable : Observable<Boolean>

        fun onRefresh()

        fun onUserDataRequested(id: Int) : UserEntity
    }

}