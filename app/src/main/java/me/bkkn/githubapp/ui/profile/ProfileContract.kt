package me.bkkn.githubapp.ui.profile

import me.bkkn.githubapp.domain.entities.UserEntity

interface ProfileContract {
    // contracts for view-presenter interaction

    interface View{
        // do minimal things needed for View
        fun showUser(user: UserEntity)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
    }

    interface Presenter{
        // logic is here
        fun attach(view:View)
        fun detach()
        fun onLaunched(userId: Int)
    }
}