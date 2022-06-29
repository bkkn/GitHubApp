package me.bkkn.githubapp.ui.users

import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

class UsersPresenter(private val usersRepo: UsersRepo) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    private var userList: List<UserEntity>? = null
    private var loadingError: Throwable? = null
    private var inProgress: Boolean= false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgress(inProgress)
        userList?.let { view.showUsers(it) }
        loadingError?.let { view.showError(it) }

    }

    override fun detach() {
        this.view = null
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        view?.showProgress(true)
        inProgress = true
        usersRepo.getUsers(
            onSuccess = {
                view?.showProgress(false)
                view?.showUsers(it)
                userList = it
                loadingError = null
                inProgress = false
            },
            onError = {
                view?.showProgress(false)
                view?.showError(it)
                loadingError = it
                inProgress = false

            }
        )
    }
}