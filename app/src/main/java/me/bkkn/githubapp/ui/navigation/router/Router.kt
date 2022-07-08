package me.bkkn.githubapp.ui.navigation.router

import me.bkkn.githubapp.ui.users.MainActivity
import java.lang.ref.WeakReference

class Router {
    companion object {
        fun launchProfileActivity(userId: Int, mainActivity: MainActivity) {
            WeakReference(mainActivity).get()!!.activityLauncher.launch(userId)
        }
    }
}