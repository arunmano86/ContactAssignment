package com.coding.assignment.ui.add

import android.content.Intent
import android.util.Log
import com.coding.assignment.api.ApiServiceInterface
import com.coding.assignment.models.User
import com.coding.assignment.models.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactAddPresenter : ContactAddContract.Presenter{

    private val subscriptions = CompositeDisposable()
    private lateinit var view: ContactAddContract.View
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    var user : User = User(-1, "", "", "")

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ContactAddContract.View) {
        Log.d("ContactAddPresenter", "======attach==========>")
        this.view = view
    }

    override fun createUser(userMap: HashMap<String, String>) {
        Log.d("ContactAddPresenter", "======createUser==========>")
        this.view.showProgress(true)

        var subscribe = api.createUser(userMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userAdd: User ->
                    userAdd.avatar = ""
                    Log.d("ContactAddPresenter", "======createUser==========>$userAdd")
                    view.showProgress(false)
                    view.isUpdateData(true, userAdd)

                    Log.d("ContactAddPresenter", "======createUser==========>$userAdd")
                }, { error ->
                    view.showProgress(false)
                    view.isUpdateData(false, user)
                    view.showErrorMessage(error.localizedMessage)
                })

        subscriptions.add(subscribe)
    }

    override fun backContactDetail(user: User) {
        view.isUpdateData(false, user)
    }

    override fun cancelAction() {
        view.isUpdateData(false, user)
    }
}