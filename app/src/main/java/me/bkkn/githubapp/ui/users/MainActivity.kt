package me.bkkn.githubapp.ui.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import me.bkkn.githubapp.App.Const.EXTRA_USER_KEY
import me.bkkn.githubapp.databinding.ActivityMainBinding
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import me.bkkn.githubapp.ui.profile.ProfileActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()
    private val viewModelDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var usersRepo: UsersRepo

    private val viewModel: UsersViewModel by lazy{UsersViewModel(usersRepo)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app.appComponent.inject(this)

        initViews()
        initViewModel()
    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        super.onDestroy()
    }

    private fun initViewModel() {
        //  viewModel = extractViewModel()
        prepareObservables()
    }

    private fun prepareObservables() {
        viewModelDisposable.addAll(
            viewModel.progressObservable.subscribe { showProgress(it) },
            viewModel.usersObservable.subscribe { showUsers(it) },
            viewModel.errorObservable.subscribe { showError(it) },
            binding.refreshButton.btnObservable.subscribe { viewModel.onRefresh() }
        )
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

    private fun showProfile(id: Int) {
        val user = viewModel.onUserDataRequested(id)
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(EXTRA_USER_KEY, user)
        startActivity(intent)
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