package com.coding.assignment.ui.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.models.User
import com.coding.assignment.ui.details.ContactDetailContract
import com.coding.assignment.ui.details.ContactDetailsActivity
import com.coding.assignment.ui.list.ContactListActivity
import com.coding.assignment.util.Constants
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_contact.*
import kotlinx.android.synthetic.main.content_contact.*
import kotlinx.android.synthetic.main.user_contact_edit.*
import javax.inject.Inject

class ContactEditActivity : AppCompatActivity(), ContactEditContract.View {


    @Inject
    lateinit var presenter: ContactEditContract.Presenter

    lateinit var user: User
    var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_contact_edit)

        Log.d(ContactEditActivity.TAG, "======onCreate==========>")
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    fun onClickDone(view: View) {
        Log.d(ContactEditActivity.TAG, "======onClickDone==========>")
        hideKeyboard()
        personFirstNameLayout.error = null
        personLastNameLayout.error = null
        if (personFirstNameEdt.text.isNullOrEmpty()) {
            personFirstNameLayout.error = "Enter the First Name"
            return
        }

        if (personLastNameEdt.text.isNullOrEmpty()) {
            personLastNameLayout.error = "Enter the Last Name"
            return
        }

        user.first_name = personFirstNameEdt.text.toString()
        user.last_name = personLastNameEdt.text.toString()
        presenter.updateUser(user)
    }

    fun onClickCancel(view: View) {
        presenter.cancelAction()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressBarEdit.visibility = View.VISIBLE
        } else {
            progressBarEdit.visibility = View.GONE
        }
    }

    override fun showErrorMessage(error: String) {
        Log.e("Error", error)
    }

    private fun initView() {
        personIdLayout.visibility = View.VISIBLE
        Log.d(ContactEditActivity.TAG, "======initView==========>$presenter")
        var bundle :Bundle ?=intent.extras
        position = intent.getIntExtra(Constants.DATA_USER_POSITION, -1)
        user  = bundle!!.getParcelable(Constants.DATA_USER_OBJECT)
        presenter.loadData(user, position)
    }

    override fun loadDataSuccess(user: User) {
        personFirstNameEdt.setText(user.first_name)
        personLastNameEdt.setText(user.last_name)
        personIdTxt.text = user.id.toString()

        if(!user.avatar.isNullOrEmpty()) {
            val transformation = RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1f)
                    .cornerRadiusDp(140f)
                    .oval(false)
                    .build()
            Picasso.with(this).load(user.avatar).transform(transformation).into(profileContactPhotoEdit)
        } else {
            Picasso.with(this).load(com.coding.assignment.R.drawable.default_user).into(profileContactPhotoEdit)
        }
    }

    override fun isUpdateData(isUpdate: Boolean) {
        if(isUpdate) {
            val result = Intent()
            result.putExtra(Constants.DATA_USER_POSITION, position);
            result.putExtra(Constants.DATA_USER_OBJECT, user)
            setResult(Activity.RESULT_OK, result)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        val TAG: String = "ContactEditActivity"
    }
}