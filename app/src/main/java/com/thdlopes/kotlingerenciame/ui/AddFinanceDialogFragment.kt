package com.thdlopes.kotlingerenciame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.databinding.FragmentAddFinanceDialogBinding

class AddFinanceDialogFragment : DialogFragment() {

    private var _binding: FragmentAddFinanceDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFinanceDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


}