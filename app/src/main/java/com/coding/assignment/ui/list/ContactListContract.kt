package com.coding.assignment.ui.list

import com.coding.assignment.base.BaseContract
import com.coding.assignment.models.User

class ContactListContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(list: List<User>)
    }

    interface Presenter: BaseContract.Presenter<ContactListContract.View> {
        fun loadData()
        fun loadDataAll()
        fun deleteItem(item: User)
        fun updateItem(item: User)
    }
}