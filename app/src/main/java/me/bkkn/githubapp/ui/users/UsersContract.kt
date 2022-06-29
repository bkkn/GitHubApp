package me.bkkn.githubapp.ui.users

import me.bkkn.githubapp.domain.entities.UserEntity

interface UsersContract {
    // contracts for view-presenter interaction

    interface View{
        // do minimal things needed for View
        fun showUsers(data: List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)

    }

    interface Presenter{
        // logic is here
        fun attach(view:View)
        fun detach(view:View)

        fun onRefresh()
    }

}