package me.bkkn.githubapp.ui.router

import android.content.Intent
import me.bkkn.githubapp.ui.profile.ProfileActivity
import me.bkkn.githubapp.ui.users.MainActivity
import java.lang.ref.WeakReference

class Router : RouterInput {

    companion object Const {
        const val EXTRA_USER_KEY = "extra_user_key"
        fun launchActivity(userId: Int, mainActivity: MainActivity) {
            Router().apply {
                activity = WeakReference<MainActivity>(mainActivity)
                val intent = determineNextScreen()
                passDataToNextScreen(userId, intent)
                activity!!.get()!!.startActivity(intent)
            }
        }
    }

    var activity: WeakReference<MainActivity>? = null

    override fun determineNextScreen(): Intent {
        //Based on some data decide what is the next screen
        return Intent(activity!!.get(), ProfileActivity::class.java)
    }

    override fun passDataToNextScreen(userId: Int, intent: Intent?) {
        //Based on the position or some other data decide the data for the next scene
        intent?.putExtra(EXTRA_USER_KEY, userId)
    }
}