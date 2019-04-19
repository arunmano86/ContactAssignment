package com.coding.assignment.ui.list

import com.coding.assignment.base.BaseContract

class ContactListContract {
    interface View: BaseContract.View {
        //fun showAboutFragment()
        //fun showListFragment()
    }

    interface Presenter: BaseContract.Presenter<ContactListContract.View> {
        //fun onDrawerOptionAboutClick()
    }
}