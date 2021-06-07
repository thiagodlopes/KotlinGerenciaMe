package com.thdlopes.kotlingerenciame.ui

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.thdlopes.kotlingerenciame.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    private var newEmail = ""
    private var newName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            val uid = firebaseUser.uid
            val name = firebaseUser.displayName
            binding.textViewUserEmail.text = email
            binding.textViewShowUid.text = uid
            binding.textViewUserName.text = name
        }else{
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var email = firebaseUser!!.email
        checkUser()

        binding.textViewEditName.setOnClickListener {
            validateNewName()
        }

        binding.textViewEditEmail.setOnClickListener {
            validateNewEmail()
        }

        binding.textViewResetPassword.setOnClickListener {
            firebaseAuth.sendPasswordResetEmail(email!!)
            Toast.makeText(
                requireContext(),
                "Verifique seu e-mail para alterar a senha",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateNewEmail() {
        newEmail = binding.editTextNewEmail.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            binding.editTextNewEmail.error = "Email inválido"
        } else{
            createDialog()
        }
    }

    private fun createDialog() {
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        AlertDialog.Builder(requireContext()).also {
            it.setTitle("Confirmar alteração de e-mail")
            it.setMessage("Você precisará relogar depois de alterar seu e-mail e verificá-lo.Tem certeza que quer alterar seu e-mail para $newEmail?")
            it.setPositiveButton("Sim, alterar"){ dialog, which ->
                firebaseUser!!.updateEmail(newEmail).addOnSuccessListener {
                    firebaseUser.sendEmailVerification()
                    firebaseAuth.signOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
                Toast.makeText(
                    context,
                    "E-mail alterado. Verifique o novo e-mail para prosseguir.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.create().show()
    }

    private fun validateNewName() {
        newName = binding.editTextNewName.text.toString().trim()
        if (newName.isEmpty()){
            binding.editTextNewName.error = "Campo em branco"
        } else {
            updateName()
        }
    }

    private fun updateName(){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var updateProfileName = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()
        firebaseUser?.updateProfile(updateProfileName)
            ?.addOnSuccessListener {
                Toast.makeText(requireContext(), "Nome alterado.", Toast.LENGTH_SHORT).show()
                reloadFragment()
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Não foi possível concluir devido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun reloadFragment(){
        checkUser()
        binding.editTextNewName.text?.clear()
       }
}