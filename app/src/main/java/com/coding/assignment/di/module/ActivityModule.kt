package com.coding.assignment.di.module

import android.app.Activity
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
    fun providePresenter(): ContactListContract.Presenter {
        return ContactListPresenter()
    }

}