package me.bkkn.githubapp.ui.users

import android.content.Intent
import me.bkkn.githubapp.ui.users.Router.Const.EXTRA_USER_KEY
import java.lang.ref.WeakReference

class Router : RouterInput {

    object Const {
        const val EXTRA_USER_KEY = "extra_user_key"
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

    companion object {
        fun launchActivity(userId: Int, mainActivity: MainActivity) {
            Router().apply {
                activity = WeakReference<MainActivity>(mainActivity)
                val intent = determineNextScreen()
                passDataToNextScreen(userId, intent)
                activity!!.get()!!.startActivity(intent)
            }
        }
    }
}