package com.coding.assignment.ui.details

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.models.User
import com.coding.assignment.ui.list.ContactListActivity
import com.coding.assignment.ui.list.ContactListContract
import com.coding.assignment.util.Constants
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_contact.*
import javax.inject.Inject

class UserDetailsActivity : AppCompatActivity(), ContactDetailContract.View {

    @Inject
    lateinit var presenter: ContactDetailContract.Presenter

    lateinit var user: User
    var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_contact)

        Log.d(ContactListActivity.TAG, "======onCreate==========>")
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    private fun initView() {
        Log.d(ContactListActivity.TAG, "======initView==========>$presenter")
        var bundle :Bundle ?=intent.extras
        position = intent.getIntExtra(Constants.DATA_USER_POSITION, -1)
        user  = bundle!!.getParcelable(Constants.DATA_USER_OBJECT)
        presenter.loadData(user, position)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    override fun loadDataSuccess(user: User) {
        personFirstName.text = user.first_name
        personLastName.text = user.last_name
        personId.text = user.id.toString()

        if(!user.avatar.isNullOrEmpty()) {
            val transformation = RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1f)
                    .cornerRadiusDp(140f)
                    .oval(false)
                    .build()
            Picasso.with(this).load(user.avatar).transform(transformation).into(profileContactPhoto)
        } else {
            Picasso.with(this).load(R.drawable.default_user).into(profileContactPhoto)
        }
    }
}