package me.bkkn.githubapp.ui.users

import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersContract {
    // contracts for view-presenter interaction

    interface View{
        // do minimal things needed for View
        fun showUsers(users: List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
        fun showProfile(id: Int)

    }

    interface Presenter{
        // logic is here
        fun attach(view:View)
        fun detach()

        fun onRefresh()

        fun onUserDataRequested(id: Int) : UserEntity
    }

}