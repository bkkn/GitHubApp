package me.bkkn.githubapp.ui.router

import android.content.Intent




interface RouterInput {
    fun determineNextScreen(): Intent
    fun passDataToNextScreen(position: Int, intent: Intent?)
}