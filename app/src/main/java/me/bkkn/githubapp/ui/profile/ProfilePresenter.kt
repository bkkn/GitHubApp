package me.bkkn.githubapp.ui.profile

import android.os.Handler
import android.os.Looper
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

class ProfilePresenter(private val usersRepo: UsersRepo) : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private var user: UserEntity? = null
    private var inProgress: Boolean = false

    override fun attach(view: ProfileContract.View) {
        this.view = view
        view.showProgress(inProgress)
        user?.let { view.showUser(it) }
    }

    override fun detach() {
        this.view = null
    }

    override fun onLaunched(userId : Int) {
        loadUserData(userId)
    }

    private fun loadUserData(userId: Int) {
        view?.showProgress(true)
        inProgress = true
        Handler(Looper.getMainLooper()).postDelayed({
            usersRepo.getUsers(
                onSuccess = {
                    view?.showProgress(false)
                    user = it[userId]
                    view?.showUser(it[userId])
                    inProgress = false
                },
                onError = {
                    view?.showProgress(false)
                    view?.showError(it)
                    inProgress = false
                }
            )
        }, 5_000)


    }

}