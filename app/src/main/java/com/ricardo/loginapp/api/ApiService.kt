package com.ricardo.loginapp.api

import com.ricardo.loginapp.model.AuthResponse
import com.ricardo.loginapp.model.LoginRequest
import com.ricardo.loginapp.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
