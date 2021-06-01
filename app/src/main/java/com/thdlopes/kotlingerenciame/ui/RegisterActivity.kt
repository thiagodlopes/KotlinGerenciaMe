package com.thdlopes.kotlingerenciame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private  lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.buttonRegister.setOnClickListener {

            validateData()
        }

    }

    private fun validateData() {
        email = binding.editTextRegisterEmail.text.toString().trim()
        password = binding.editTextRegisterPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextRegisterEmail.error = "Email inválido"
        }
        else if (password.isEmpty()){
            binding.editTextRegisterPassword.error = "Campo em branco"

        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                Toast.makeText(this, "Cadastro realizado com o email $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                Toast.makeText(this, "Cadastro falhou devido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}