package com.example.helloworld.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {
    @GET("/gists/{gist_id}")
    fun getGist(@Path("gist_id") gistId: String): Call<Gist>
}