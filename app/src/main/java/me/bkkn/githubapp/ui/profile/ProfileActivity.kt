package me.bkkn.githubapp.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import me.bkkn.githubapp.app
import me.bkkn.githubapp.databinding.ActivityProfileBinding
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.ui.navigation.NavigationContract.Const.EXTRA_USER_KEY

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = extractPresenter()

        presenter.attach(this)

        val userId = intent.extras?.getInt(EXTRA_USER_KEY)
        userId?.let { presenter.onLaunched(it) }
    }

    private fun extractPresenter(): ProfileContract.Presenter {
        return lastCustomNonConfigurationInstance as? ProfileContract.Presenter
            ?: ProfilePresenter(app.usersRepo)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showUser(user: UserEntity) {
        binding.profileActivityLoginTextView.text = user?.login
        binding.profileActivityAvatarImageView.load(user?.avatarUrl)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(inProgress: Boolean) {
        binding.profileActivityProgressBar.isVisible = inProgress
        binding.profileActivityAvatarImageView.isVisible = !inProgress
        binding.profileActivityLoginTextView.isVisible = !inProgress
    }
}