package me.bkkn.githubapp.ui.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import me.bkkn.githubapp.App
import me.bkkn.githubapp.R
import me.bkkn.githubapp.databinding.ActivityMainBinding
import me.bkkn.githubapp.databinding.ActivityProfileBinding
import me.bkkn.githubapp.domain.entities.UserEntity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.getSerializable(App.EXTRA_USER_KEY) as UserEntity
        binding.profileActivityLoginTextView.text = user.login
        binding.profileActivityAvatarImageView.load(user.avatarUrl)
    }
}