package com.coding.assignment.di.component

import com.coding.assignment.BaseApp
import com.coding.assignment.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApp)

}