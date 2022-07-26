package me.bkkn.githubapp.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.bkkn.dil.get
import me.bkkn.githubapp.app
import me.bkkn.githubapp.databinding.ActivityProfileBinding
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.ui.navigation.NavigationContract.Const.EXTRA_USER_KEY
import me.bkkn.githubapp.ui.users.UsersContract
import me.bkkn.githubapp.ui.users.UsersViewModel

class ProfileActivity : AppCompatActivity(){

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private val viewModelDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModel()

        val userId = intent.extras?.getInt(EXTRA_USER_KEY)
        userId?.let { viewModel.onLaunched(it) }
    }

    private fun initViewModel() {
        viewModel = extractViewModel()
        prepareObservables()
    }

    private fun prepareObservables() {
        viewModelDisposable.addAll(
            viewModel.progressObservable.subscribe { showProgress(it) },
            viewModel.profileObservable.subscribe { showUser(it) },
            viewModel.errorObservable.subscribe { showError(it) },
        )
    }

    private fun extractViewModel(): ProfileViewModel {
        return lastCustomNonConfigurationInstance as? ProfileViewModel
            ?: ProfileViewModel()
    }

    override fun onRetainCustomNonConfigurationInstance(): ProfileViewModel {
        return viewModel
    }

    private fun initViews() {
        showProgress(false)
    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        super.onDestroy()
    }

    fun showUser(user: UserEntity) {
        binding.profileActivityLoginTextView.text = user?.login
        binding.profileActivityAvatarImageView.load(user?.avatarUrl)
    }

    fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    fun showProgress(inProgress: Boolean) {
        binding.profileActivityProgressBar.isVisible = inProgress
        binding.profileActivityAvatarImageView.isVisible = !inProgress
        binding.profileActivityLoginTextView.isVisible = !inProgress
    }

}