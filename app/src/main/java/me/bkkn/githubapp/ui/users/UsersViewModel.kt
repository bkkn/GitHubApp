package me.bkkn.githubapp.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo
import me.bkkn.githubapp.utils.SingleEventLiveData
import java.lang.IllegalStateException

class UsersViewModel(private val usersRepo: UsersRepo) : UsersContract.ViewModel {

    override val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData()
    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData<Throwable>()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData<Boolean>()

    override fun onRefresh() {
        loadData()
    }

    override fun onUserDataRequested(id: Int): UserEntity {
       return usersLiveData.value?.get(id) ?: UserEntity()
    }

    private fun loadData() {
        progressLiveData.mutable().postValue(true)
        usersRepo.getUsers(
            onSuccess = {
                progressLiveData.mutable().postValue(false)
                usersLiveData.mutable().postValue(it)
            },
            onError = {
                progressLiveData.mutable().postValue(false)
                errorLiveData.mutable().postValue(it)
            }
        )
    }

    private fun <T> LiveData<T>.mutable() : MutableLiveData<T>{
        return this as? MutableLiveData<T>?:throw IllegalStateException("Is is not MutableLiveData")
    }
}