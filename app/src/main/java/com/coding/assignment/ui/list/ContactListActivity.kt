package com.coding.assignment.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.coding.assignment.R
import com.coding.assignment.di.component.DaggerActivityComponent
import com.coding.assignment.di.module.ActivityModule
import com.coding.assignment.models.User
import com.coding.assignment.ui.details.UserDetailsActivity
import com.coding.assignment.util.Constants
import kotlinx.android.synthetic.main.activity_list_contact.*
import javax.inject.Inject

class ContactListActivity : AppCompatActivity(), ContactListContract.View, ListAdapter.onItemClickListener {

    @Inject
    lateinit var presenter: ContactListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)

        Log.d(TAG, "======onCreate==========>")
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    private fun initView() {
        Log.d(TAG, "======initView==========>$presenter")
        presenter.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorMessage(error: String) {
        Log.e("Error", error)
    }

    override fun loadDataSuccess(list: List<User>) {
        Log.d(TAG, "======loadDataSuccess==========>${list.size}")
        var adapter = ListAdapter(applicationContext, list.toMutableList(), this)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView!!.setAdapter(adapter)
    }

    override fun itemRemoveClick(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemDetail(user: User, position: Int) {
        Log.d(TAG, "$position+======loadDataSuccess==========>${user.first_name}")
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(Constants.DATA_USER_POSITION, position);
        intent.putExtra(Constants.DATA_USER_OBJECT, user)
        startActivityForResult(intent, Constants.ACTIVITY_RESULT_USER_DETAILS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ) {
            when(requestCode) {
                Constants.ACTIVITY_RESULT_USER_DETAILS -> {
                    Log.d(TAG, "======ACTIVITY_RESULT_USER_DETAILS==========>")
                }
                Constants.ACTIVITY_RESULT_USER_EDIT -> {
                    Log.d(TAG, "======ACTIVITY_RESULT_USER_EDIT==========>")
                }
                Constants.ACTIVITY_RESULT_USER_CREATE -> {
                    Log.d(TAG, "======ACTIVITY_RESULT_USER_CREATE==========>")
                }
            }
        }
    }

    companion object {
        val TAG: String = "ContactListActivity"
    }
}