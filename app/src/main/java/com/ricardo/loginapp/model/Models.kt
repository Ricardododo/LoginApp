package com.ricardo.loginapp.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    @SerializedName("created_at")
    val createdAt: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val message: String,
    val token: String? = null,
    val user: User? = null
)

data class ApiError(
    val message: String
)
