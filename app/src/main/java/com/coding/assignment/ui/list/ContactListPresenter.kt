package com.coding.assignment.ui.list

import android.util.Log
import com.coding.assignment.api.ApiServiceInterface
import com.coding.assignment.models.User
import com.coding.assignment.models.Users
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactListPresenter : ContactListContract.Presenter{
    private val subscriptions = CompositeDisposable()
    private lateinit var view: ContactListContract.View
    private val api: ApiServiceInterface = ApiServiceInterface.create()

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ContactListContract.View) {
        Log.d("ContactListPresenter", "======attach==========>")
        this.view = view
        //view.showListFragment() // as default
    }

    override fun loadData() {
        Log.d("ContactListPresenter", "======loadData==========>")
        this.view.showProgress(true)
        var subscribe = api.getUsersList(1, 10).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users: Users? ->
                    view.showProgress(false)
                    view.loadDataSuccess(users!!.data)
                }, { error ->
                    view.showProgress(false)
                    view.showErrorMessage(error.localizedMessage)
                })

        subscriptions.add(subscribe)
    }

    override fun loadDataAll() {
    }

    override fun deleteItem(item: User) {

    }

    override fun updateItem(item: User) {

    }


//    override fun onDrawerOptionAboutClick() {
//        view.showAboutFragment()
//    }
}