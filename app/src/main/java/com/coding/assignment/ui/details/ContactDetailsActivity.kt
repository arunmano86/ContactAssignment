package com.coding.assignment.ui.details

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.models.User
import com.coding.assignment.ui.edit.ContactEditActivity
import com.coding.assignment.ui.list.ContactListActivity
import com.coding.assignment.util.Constants
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_contact.*
import javax.inject.Inject



class ContactDetailsActivity : AppCompatActivity(), ContactDetailContract.View {

    @Inject
    lateinit var presenter: ContactDetailContract.Presenter

    lateinit var user: User
    var position: Int = -1
    var isDataUpdated : Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.coding.assignment.R.layout.content_contact)

        Log.d(ContactDetailsActivity.TAG, "======onCreate==========>")
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    private fun initView() {
        Log.d(ContactDetailsActivity.TAG, "======initView==========>$presenter")
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
        personId.text = user.id.toString()
        personName.text = "${user.first_name} ${user.last_name}"

        if(!user.avatar.isNullOrEmpty()) {
            val transformation = RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1f)
                    .cornerRadiusDp(140f)
                    .oval(false)
                    .build()
            Picasso.with(this).load(user.avatar).transform(transformation).into(profileContactPhoto)
        } else {
            Picasso.with(this).load(com.coding.assignment.R.drawable.default_user).into(profileContactPhoto)
        }
    }

    fun onClickEditContact(view: View) {
            val intent = Intent(this, ContactEditActivity::class.java)
            intent.putExtra(Constants.DATA_USER_POSITION, position);
            intent.putExtra(Constants.DATA_USER_OBJECT, user)
            startActivityForResult(intent, Constants.ACTIVITY_RESULT_USER_EDIT)
    }

    fun onClickHomeContact(view: View) {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ) {
            when(requestCode) {
                Constants.ACTIVITY_RESULT_USER_EDIT -> {
                    Log.d(ContactListActivity.TAG, "======ACTIVITY_RESULT_USER_EDIT==========>")
                    var bundle :Bundle ?=data!!.extras
                    position = data!!.getIntExtra(Constants.DATA_USER_POSITION, -1)
                    user  = bundle!!.getParcelable(Constants.DATA_USER_OBJECT)
                    presenter.loadData(user, position)
                    isDataUpdated = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if(isDataUpdated) {
            val result = Intent()
            result.putExtra(Constants.DATA_USER_POSITION, position);
            result.putExtra(Constants.DATA_USER_OBJECT, user)
            setResult(Activity.RESULT_OK, result)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    companion object {
        val TAG: String = "ContactDetailsActivity"
    }

}