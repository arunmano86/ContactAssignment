package com.coding.assignment.ui.list

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import javax.inject.Inject

class ContactListActivity : AppCompatActivity(), ContactListContract.View {

    @Inject
    lateinit var presenter: ContactListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)
        injectDependency()

        presenter.attach(this)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }
}