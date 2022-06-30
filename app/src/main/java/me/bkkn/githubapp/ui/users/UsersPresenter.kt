package me.bkkn.githubapp.ui.users

import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

class UsersPresenter(private val usersRepo: UsersRepo) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    private var userList: List<UserEntity>? = null
    private var inProgress: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgress(inProgress)
        userList?.let { view.showUsers(it) }
    }

    override fun detach() {
        this.view = null
    }

    override fun onRefresh() {
        loadData()
    }

    override fun onUserDataRequested(id : Int): UserEntity {
        return userList?.get(id) ?: UserEntity()
    }

    private fun loadData() {
        view?.showProgress(true)
        inProgress = true
        usersRepo.getUsers(
            onSuccess = {
                view?.showProgress(false)
                view?.showUsers(it)
                userList = it
                inProgress = false
            },
            onError = {
                view?.showProgress(false)
                view?.showError(it)
                inProgress = false
            }
        )
    }
}