package me.bkkn.githubapp.ui.navigation.router

import android.content.Intent




interface RouterInput {
    fun determineNextScreen(): Intent
    fun passDataToNextScreen(position: Int, intent: Intent?)
}