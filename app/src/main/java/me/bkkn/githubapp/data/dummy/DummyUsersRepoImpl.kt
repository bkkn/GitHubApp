package me.bkkn.githubapp.data.dummy

import android.os.Handler
import io.reactivex.rxjava3.core.Single
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

private const val DATA_LOADING_FAKE_DELAY = 5_000L

class FakeUsersRepoImpl(
    private val handler: Handler
) : UsersRepo {

    private val data = listOf<UserEntity>(
        UserEntity("mojombo", 1, "https://avatars.githubusercontent.com/u/1?v=4"),
        UserEntity("defunkt", 2, "https://avatars.githubusercontent.com/u/2?v=4"),
        UserEntity("pjhyett", 3, "https://avatars.githubusercontent.com/u/3?v=4"),
    )

    override fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        handler.postDelayed({
            onSuccess(data)
        }, DATA_LOADING_FAKE_DELAY)
    }

    override fun getUsers(): Single<List<UserEntity>> = Single.just(data)
}