package com.coding.assignment.ui.edit

import android.content.Intent
import android.util.Log
import com.coding.assignment.api.ApiServiceInterface
import com.coding.assignment.models.User
import com.coding.assignment.models.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactEditPresenter : ContactEditContract.Presenter{

    private val subscriptions = CompositeDisposable()
    private lateinit var view: ContactEditContract.View
    private val api: ApiServiceInterface = ApiServiceInterface.create()

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ContactEditContract.View) {
        Log.d("ContactListPresenter", "======attach==========>")
        this.view = view
    }

    override fun loadData(user: User, position: Int) {
        Log.d("ContactListPresenter", "======loadData==========>")
        view.loadDataSuccess(user)
    }

    override fun updateUser(user: User) {
        Log.d("ContactListPresenter", "======loadData==========>")
        this.view.showProgress(true)

        var subscribe = api.updateItem(user.id.toString(), user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user: User ->
                    view.showProgress(false)
                    view.isUpdateData(true)

                }, { error ->
                    view.showProgress(false)
                    view.isUpdateData(false)
                    view.showErrorMessage(error.localizedMessage)
                })

        subscriptions.add(subscribe)
    }

    override fun backContactDetail(user: User) {
        view.isUpdateData(false)
    }

    override fun cancelAction() {
        view.isUpdateData(false)
    }
}