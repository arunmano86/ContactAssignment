package com.coding.assignment.di.component

import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.ui.details.UserDetailsActivity
import com.coding.assignment.ui.list.ContactListActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(contactListActivity: ContactListActivity)

    fun inject(userDetailsActivity: UserDetailsActivity)
}