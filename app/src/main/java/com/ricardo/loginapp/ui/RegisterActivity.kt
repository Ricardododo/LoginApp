package com.ricardo.loginapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ricardo.loginapp.api.ApiClient
import com.ricardo.loginapp.databinding.ActivityRegisterBinding
import com.ricardo.loginapp.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validateInputs(nombre, email, password, confirmPassword)) {
                performRegister(nombre, email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInputs(
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (nombre.isEmpty()) {
            binding.tilNombre.error = "El nombre es requerido"
            isValid = false
        } else if (nombre.length < 3) {
            binding.tilNombre.error = "El nombre debe tener al menos 3 caracteres"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        if (email.isEmpty()) {
            binding.tilEmail.error = "El email es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Ingresa un email válido"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "La contraseña es requerida"
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Confirma tu contraseña"
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        return isValid
    }

    private fun performRegister(nombre: String, email: String, password: String) {
        showLoading(true)

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.register(RegisterRequest(nombre, email, password))

                if (response.isSuccessful) {
                    val body = response.body()
                    Toast.makeText(this@RegisterActivity, body?.message ?: "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        com.ricardo.loginapp.model.ApiError(
                            com.google.gson.Gson().fromJson(errorBody, Map::class.java)["message"] as? String ?: "Error desconocido"
                        ).message
                    } catch (e: Exception) {
                        "Error al registrar usuario"
                    }
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !isLoading
        binding.btnRegister.text = if (isLoading) "" else "Registrarse"
    }
}
