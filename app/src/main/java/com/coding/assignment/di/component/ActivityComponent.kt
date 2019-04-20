package com.coding.assignment.di.component

import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.ui.add.ContactAddActivity
import com.coding.assignment.ui.details.ContactDetailsActivity
import com.coding.assignment.ui.edit.ContactEditActivity
import com.coding.assignment.ui.list.ContactListActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(contactListActivity: ContactListActivity)

    fun inject(contactDetailsActivity: ContactDetailsActivity)

    fun inject(editContactActivity: ContactEditActivity)

    fun inject(addContactActivity: ContactAddActivity)
}