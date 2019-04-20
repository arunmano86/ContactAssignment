package com.coding.assignment.ui.details

import com.coding.assignment.base.BaseContract
import com.coding.assignment.models.User

class ContactDetailContract {
    interface View: BaseContract.View {
        //fun showProgress(show: Boolean)
        //fun showErrorMessage(error: String)
        fun loadDataSuccess(user: User)
    }

    interface Presenter: BaseContract.Presenter<ContactDetailContract.View> {
        fun loadData(user: User, position: Int)
    }
}