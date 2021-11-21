package com.example.jpgtopngconvertor.presentation.converter

import android.net.Uri
import com.example.jpgtopngconvertor.data.converter.ConverterImpl
import com.example.jpgtopngconvertor.presentation.scheduler.DefaultSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class ConverterPresenter(
    private val converter: ConverterImpl,
    private val schedulers: DefaultSchedulers
) : MvpPresenter<ConverterView>() {

    private val disposables = CompositeDisposable()

    operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }

    fun convert(uri: Uri) {
        viewState.showContent(uri)
        viewState.showLoading()

        disposables += converter
            .convert(uri)
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribe(
                viewState::showContent,
                viewState::showError
            )
    }


    fun cancel() {
        viewState.showContent(null)
        disposables.clear()
    }

    override fun onDestroy() = disposables.clear()
}