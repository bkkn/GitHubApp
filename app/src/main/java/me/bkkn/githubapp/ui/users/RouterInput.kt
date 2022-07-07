package me.bkkn.githubapp.ui.users

import android.content.Intent




interface RouterInput {
    fun determineNextScreen(): Intent
    fun passDataToNextScreen(position: Int, intent: Intent?)
}