package com.thdlopes.kotlingerenciame.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.databinding.FragmentEmailBinding
import java.lang.Exception

class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSendEmail.setOnClickListener {

            val email = "thiagodaniellopes99@gmail.com"
            val subject = binding.editTextSubject.text.toString()
            val message = binding.editTextMessage.text.toString()

            val addresses = email.split(",".toRegex()).toTypedArray()

            val intent = Intent(Intent.ACTION_SENDTO).apply {

                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)

            }

            try {
                startActivity(intent)
            } catch (e: Exception){
                Toast.makeText(requireContext(), "O App requisitado não está instalado", Toast.LENGTH_SHORT).show()
            }
        }
    }

}