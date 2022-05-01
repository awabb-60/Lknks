package com.awab.links.model

import retrofit2.Response
import retrofit2.http.*


interface Is_gdLinkShortenerApi {
    @GET("/create.php")
    suspend fun getShortLink(
        @Query("format")
        format: String = "json",

        @Query("url")
        link: String

    ): Response<Is_gdRespons>
}