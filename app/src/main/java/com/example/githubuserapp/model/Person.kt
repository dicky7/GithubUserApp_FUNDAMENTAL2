package com.example.githubuserapp.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person (
    var login_username: String? = "",
    var avatar_url: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: Int = 0,
    var follower: Int = 0,
    var following: Int = 0
):Parcelable