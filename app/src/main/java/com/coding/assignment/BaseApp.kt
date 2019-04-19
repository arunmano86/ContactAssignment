package com.coding.assignment

import android.app.Application
import com.coding.assignment.di.component.ApplicationComponent
import com.coding.assignment.di.component.DaggerApplicationComponent
import com.coding.assignment.di.module.ApplicationModule

class BaseApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}