package com.ricardo.loginapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ricardo.loginapp.api.ApiClient
import com.ricardo.loginapp.databinding.ActivityLoginBinding
import com.ricardo.loginapp.model.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (validateInputs(email, password)) {
                performLogin(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

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

        return isValid
    }

    private fun performLogin(email: String, password: String) {
        showLoading(true)

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.token != null) {
                        Toast.makeText(this@LoginActivity, "¡Bienvenido ${body.user?.nombre}!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity, body?.message ?: "Error en el servidor", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        com.ricardo.loginapp.model.ApiError(
                            com.google.gson.Gson().fromJson(errorBody, Map::class.java)["message"] as? String ?: "Error desconocido"
                        ).message
                    } catch (e: Exception) {
                        "Credenciales inválidas"
                    }
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
        binding.btnLogin.text = if (isLoading) "" else "Iniciar Sesión"
    }
}
