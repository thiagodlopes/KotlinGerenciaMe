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
    private var passwordConfirmation = ""

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
        passwordConfirmation = binding.editTextRegisterPasswordConfirmation.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextRegisterEmail.error = "Email inválido"
        }
        else if (password.isEmpty()){
            binding.editTextRegisterPassword.error = "Campo em branco"

        }
        else if (password != passwordConfirmation){
            binding.editTextRegisterPasswordConfirmation.error = "Os campos devem ser iguais"
            binding.editTextRegisterPassword.error = "Os campos devem ser iguais"

        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                firebaseUser?.sendEmailVerification().addOnSuccessListener() {
                    Toast.makeText(this, "Por favor verifique o $email para prosseguir", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                    .addOnFailureListener { Toast.makeText(this, "Verificação não pode enviada.", Toast.LENGTH_SHORT).show() }

            }
            .addOnFailureListener { e->
                Toast.makeText(this, "Cadastro falhou devido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}