package com.coding.assignment.models

import android.os.Parcelable
import android.os.Parcel


data class User(val id: Int, val first_name: String, val last_name: String, val avatar: String): Parcelable {

    constructor(parcel: Parcel) : this (
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}