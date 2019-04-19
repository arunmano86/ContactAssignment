package com.coding.assignment.ui.list

import io.reactivex.disposables.CompositeDisposable

class ContactListPresenter : ContactListContract.Presenter{
    private val subscriptions = CompositeDisposable()
    private lateinit var view: ContactListContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ContactListContract.View) {
        this.view = view
        //view.showListFragment() // as default
    }

//    override fun onDrawerOptionAboutClick() {
//        view.showAboutFragment()
//    }
}