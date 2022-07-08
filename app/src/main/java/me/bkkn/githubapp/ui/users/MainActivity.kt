package me.bkkn.githubapp.ui.users

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import me.bkkn.dil.get
import me.bkkn.githubapp.App.Const.EXTRA_USER_KEY
import me.bkkn.githubapp.databinding.ActivityMainBinding
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.ui.profile.ProfileActivity
import me.bkkn.githubapp.ui.navigation.NavigationContract
import me.bkkn.githubapp.ui.navigation.router.Router

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()
    private val viewModelDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: UsersContract.ViewModel

    val activityLauncher = registerForActivityResult(NavigationContract()) { result ->
        // используем result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModel()
    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        super.onDestroy()
    }

    private fun initViewModel() {
        viewModel = extractViewModel()
        prepareObservables()
    }

    private fun prepareObservables() {
        viewModelDisposable.addAll(
            viewModel.progressObservable.subscribe { showProgress(it) },
            viewModel.usersObservable.subscribe { showUsers(it) },
            viewModel.errorObservable.subscribe { showError(it) },
            binding.refreshButton.btnObservable.subscribe {
                viewModel.onRefresh()
                Toast.makeText(this, get<String>(), Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun extractViewModel(): UsersContract.ViewModel {
        return lastCustomNonConfigurationInstance as? UsersContract.ViewModel
            ?: UsersViewModel()
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.ViewModel? {
        return viewModel
    }

    private fun initViews() {
        initRecycleView()
        showProgress(false)
    }

    private fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.usersRecyclerView.isVisible = !inProgress
    }

    override fun showProfile(id: Int) {
        Router.launchProfileActivity(id,this)
    }

    private fun initRecycleView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = adapter
        adapter.setListener(object : UsersAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showProfile(position)
            }
        })
    }
}