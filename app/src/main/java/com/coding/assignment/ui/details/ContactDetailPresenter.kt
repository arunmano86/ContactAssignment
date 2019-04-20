package com.coding.assignment.ui.details

import android.util.Log
import com.coding.assignment.api.ApiServiceInterface
import com.coding.assignment.models.User
import com.coding.assignment.models.Users
import com.coding.assignment.ui.list.ContactListContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactDetailPresenter : ContactDetailContract.Presenter{
    private val subscriptions = CompositeDisposable()
    private lateinit var view: ContactDetailContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ContactDetailContract.View) {
        Log.d("ContactListPresenter", "======attach==========>")
        this.view = view
        //view.showListFragment() // as default
    }

    override fun loadData(user: User, position: Int) {
        Log.d("ContactListPresenter", "======loadData==========>")
        view.loadDataSuccess(user)
    }
}