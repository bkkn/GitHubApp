package me.bkkn.githubapp.ui.users

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import me.bkkn.githubapp.app
import me.bkkn.githubapp.databinding.ActivityMainBinding
import me.bkkn.githubapp.domain.entities.UserEntity
import me.bkkn.githubapp.domain.repos.UsersRepo

class MainActivity : AppCompatActivity(), UsersContract.View {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()
    private val usersRepo : UsersRepo by lazy { app.usersRepo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.refeshButton.setOnClickListener {
            loadData()
        }
        initRecycleView()
        showProgress(false)
    }

    private fun loadData() {
        showProgress(true)
        usersRepo.getUsers(
            onSuccess = {
                showProgress(false)
                showUsers(it)
            },
            onError = {
                showProgress(false)
                showError(it)
            }
        )
    }

    override fun showUsers(data: List<UserEntity>) {
        adapter.setData(data)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()

    }

    override fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.usersRecyclerView.isVisible = !inProgress
    }

    private fun initRecycleView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = adapter
    }

}