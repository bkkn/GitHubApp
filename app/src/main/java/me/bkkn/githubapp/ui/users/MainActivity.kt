package me.bkkn.githubapp.ui.users

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import me.bkkn.githubapp.app
import me.bkkn.githubapp.databinding.ActivityMainBinding
import me.bkkn.githubapp.domain.entities.UserEntity

class MainActivity : AppCompatActivity(), UsersContract.View {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()
    private lateinit var presenter: UsersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        presenter = extractPresenter()
        presenter.attach(this)
    }

    private fun extractPresenter(): UsersContract.Presenter {
        return lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: UsersPresenter(app.usersRepo)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.Presenter? {
        return presenter
    }

    override fun getLastCustomNonConfigurationInstance(): Any? {
        return super.getLastNonConfigurationInstance()
    }

    private fun initViews() {
        binding.refeshButton.setOnClickListener {
            presenter.onRefresh()
        }
        initRecycleView()
        showProgress(false)
    }

    override fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.usersRecyclerView.isVisible = !inProgress
    }

    override fun showProfile(id: Int) {
        Router.launchActivity(id,this)

//        val user = presenter.onUserDataRequested(id)
//        val intent = Intent(this, ProfileActivity::class.java)
//        intent.putExtra(EXTRA_USER_KEY, user)
//        startActivity(intent)
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