package com.example.jpgtopngconvertor.presentation

import android.R
import android.os.Bundle
import com.example.jpgtopngconvertor.App.Navigation.navigatorHolder
import com.example.jpgtopngconvertor.App.Navigation.router
import com.example.jpgtopngconvertor.presentation.converter.ConverterScreen
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(activity = this, R.id.content)

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: router.newRootScreen(ConverterScreen)

    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}