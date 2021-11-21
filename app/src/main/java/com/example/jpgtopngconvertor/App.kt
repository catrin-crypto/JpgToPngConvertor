package com.example.jpgtopngconvertor

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {

    companion object Navigation {

        private val cicerone: Cicerone<Router> by lazy {
            Cicerone.create(Router())
        }

        val navigatorHolder = cicerone.getNavigatorHolder()
        val router = cicerone.router

    }

}