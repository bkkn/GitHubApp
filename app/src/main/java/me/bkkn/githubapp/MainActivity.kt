package me.bkkn.githubapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import me.bkkn.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()
    private val usersRepo = FakeUsersRepoImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgress(false)
        binding.refeshButton.setOnClickListener {
            Toast.makeText(this, "refreshed", Toast.LENGTH_LONG).show()
        }

        initRecycleView()
    }

    private fun initRecycleView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = adapter
        showProgress(true)
        usersRepo.getUsers(
            onSuccess = {
                showProgress(false)
                adapter.setData(it)
            },
            onError = {
                showProgress(false)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.usersRecyclerView.isVisible = !inProgress
    }
}