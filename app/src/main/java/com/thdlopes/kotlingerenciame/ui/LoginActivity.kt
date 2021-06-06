package com.thdlopes.kotlingerenciame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.thdlopes.kotlingerenciame.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private  lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.textViewResetPassword.setOnClickListener {
            email = binding.editTextEmail.text.toString().trim()

            if (email != null && email != "") {
                firebaseAuth.sendPasswordResetEmail(email!!)
                Toast.makeText(this, "Verifique o e-mail $email para alterar a senha", Toast.LENGTH_SHORT).show()
            } else {
                binding.editTextEmail.error = "Insira seu e-mail e depois clique em 'Redefinir Senha' para seguir com a troca de senha."

            }

        }

        binding.buttonLogin.setOnClickListener {
            validadeData()
        }
    }

    private fun validadeData() {

        email = binding.editTextEmail.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextEmail.error = "Email inválido"
        } else if (password.isEmpty()){
            binding.editTextPassword.error = "Campo em branco"
        } else {
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                if (firebaseUser.isEmailVerified) {
                    Toast.makeText(this, "Entrou como  $email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()}
                else {
                    Toast.makeText(this, "Um e-mail foi enviado para $email verifique o e-mail para fazer o Login", Toast.LENGTH_SHORT).show()
                    firebaseUser.sendEmailVerification()
                }
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "Login falhou devido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null && firebaseUser.isEmailVerified){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}