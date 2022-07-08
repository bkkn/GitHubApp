package me.bkkn.githubapp.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import me.bkkn.githubapp.ui.profile.ProfileActivity

class NavigationContract : ActivityResultContract<Int, Unit>() {

    companion object Const {
        const val EXTRA_USER_KEY = "extra_user_id"
    }

    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, ProfileActivity::class.java)
            .putExtra(EXTRA_USER_KEY, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {}
}