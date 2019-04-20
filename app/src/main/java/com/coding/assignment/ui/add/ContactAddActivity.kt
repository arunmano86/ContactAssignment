package com.coding.assignment.ui.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.models.User
import com.coding.assignment.ui.edit.ContactEditActivity
import com.coding.assignment.ui.list.ContactListContract
import com.coding.assignment.util.Constants
import kotlinx.android.synthetic.main.activity_list_contact.*
import kotlinx.android.synthetic.main.user_contact_edit.*
import javax.inject.Inject

class ContactAddActivity : AppCompatActivity(), ContactAddContract.View {

    @Inject
    lateinit var presenter: ContactAddContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_contact_edit)

        Log.d(ContactEditActivity.TAG, "======onCreate==========>")
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()
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
        //user = User(null, personFirstNameEdt.text.toString(), personLastNameEdt.text.toString(), "")
        //user.first_name = personFirstNameEdt.text.toString()
        //user.last_name = personLastNameEdt.text.toString()

        var userMap : HashMap<String, String>  = HashMap<String, String>()
        userMap.put("first_name", personFirstNameEdt.text.toString())
        userMap.put("last_name", personLastNameEdt.text.toString())
        presenter.createUser(userMap)
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

    override fun isUpdateData(isUpdate: Boolean, user: User) {
        if(isUpdate) {
            val result = Intent()
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
        val TAG: String = "ContactAddActivity"
    }
}