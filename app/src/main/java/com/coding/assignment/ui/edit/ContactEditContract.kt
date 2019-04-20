package com.coding.assignment.ui.edit

import com.coding.assignment.base.BaseContract
import com.coding.assignment.models.User

class ContactEditContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(user: User)
        fun isUpdateData(isUpdate: Boolean)
    }

    interface Presenter: BaseContract.Presenter<ContactEditContract.View> {
        fun loadData(user: User, position: Int)
        fun updateUser(user: User)
        fun backContactDetail(user: User)
        fun cancelAction()
    }
}