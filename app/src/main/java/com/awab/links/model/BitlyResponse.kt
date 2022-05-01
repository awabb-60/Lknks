package com.awab.links.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BitlyResponse(
    @SerializedName("link")
    @Expose
    val link:String,

    @Expose
    @SerializedName("id")
    val id:Int,

    @Expose
    @SerializedName("description")
    val des:String,

    @Expose
    @SerializedName("message")
    val message:Int
)
