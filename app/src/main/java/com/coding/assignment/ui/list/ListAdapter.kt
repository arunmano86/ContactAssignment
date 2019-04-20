package com.coding.assignment.ui.list

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.coding.assignment.R
import com.coding.assignment.models.User
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso

class ListAdapter(private val context: Context, private val list: MutableList<User>,
                  activity: Activity): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val listener: ListAdapter.onItemClickListener

    init {
        this.listener = activity as ListAdapter.onItemClickListener
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var user = list[position]

        // holder!!.bind(post)
        holder.contactName.text = "${user.first_name} ${user.last_name}"
        if(!user.avatar.isNullOrEmpty()) {
            val transformation = RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(1f)
                    .cornerRadiusDp(60f)
                    .oval(false)
                    .build()
            Picasso.with(context).load(user.avatar).transform(transformation).into(holder.contactPhoto)
        } else {
            Picasso.with(context).load(R.drawable.default_user).into(holder.contactPhoto)
        }


        holder.layout.setOnClickListener {
            listener.itemDetail(user, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_contact, parent, false)
        return ListAdapter.ListViewHolder(itemView)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateAt(position: Int, user: User) {
        list.set(position, user)
        notifyItemChanged(position)
    }

    fun addUser(user: User) {
        list.add(user)
        notifyItemChanged(list.size - 1)
    }

    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<LinearLayout>(R.id.contact_layout)
        val contactName = itemView.findViewById<TextView>(R.id.contact_name)
        val contactPhoto = itemView.findViewById<ImageView>(R.id.contact_photo)

        fun bind(item: User) {
            // title = item.post
            // body etc.
        }
    }

    interface onItemClickListener {
        fun itemRemoveClick(user: User)
        fun itemDetail(user: User, position: Int)
    }
}