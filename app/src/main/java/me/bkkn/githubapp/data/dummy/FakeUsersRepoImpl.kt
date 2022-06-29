package me.bkkn.githubapp.data.dummy

import android.os.Handler
import android.os.Looper
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

private const val DATA_LOADING_FAKE_DELAY = 5_000L
class FakeUsersRepoImpl : UsersRepo {

    private val data = listOf<UserEntity>(
        UserEntity("mojombo",1,"https://avatars.githubusercontent.com/u/1?v=4"),
        UserEntity("defunkt",2,"https://avatars.githubusercontent.com/u/2?v=4"),
        UserEntity("pjhyett",3,"https://avatars.githubusercontent.com/u/3?v=4"),
    )

    override fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess(data)
        }, DATA_LOADING_FAKE_DELAY)
    }
}