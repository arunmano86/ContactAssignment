package com.coding.assignment.di.module

import android.app.Activity
import com.coding.assignment.ui.details.ContactDetailContract
import com.coding.assignment.ui.details.ContactDetailPresenter
import com.coding.assignment.ui.list.ContactListContract
import com.coding.assignment.ui.list.ContactListPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideListPresenter(): ContactListContract.Presenter {
        return ContactListPresenter()
    }

    @Provides
    fun provideDetailPresenter(): ContactDetailContract.Presenter {
        return ContactDetailPresenter()
    }

}