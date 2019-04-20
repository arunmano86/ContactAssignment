package com.coding.assignment.ui.add

import com.coding.assignment.base.BaseContract
import com.coding.assignment.models.User

class ContactAddContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun isUpdateData(isUpdate: Boolean, user: User)
    }

    interface Presenter: BaseContract.Presenter<ContactAddContract.View> {
        //fun loadData(user: User, position: Int)
        fun createUser(user : HashMap<String, String>)
        fun backContactDetail(user: User)
        fun cancelAction()
    }
}