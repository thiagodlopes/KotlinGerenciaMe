package com.thdlopes.kotlingerenciame.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.ChipGroup
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.data.FinanceViewModel
import com.thdlopes.kotlingerenciame.databinding.FragmentAddFinanceDialogBinding

class AddFinanceDialogFragment : DialogFragment() {

    private var _binding: FragmentAddFinanceDialogBinding? = null
    private val binding get() = _binding!!
    private var getMoviment: String? = null

    private lateinit var viewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFinanceDialogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(FinanceViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if(it == null){
                getString(R.string.added_finance)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

       getMoviment()

        binding.buttonAddFinance.setOnClickListener {
            val name = binding.editTextFinanceName.text.toString().trim()
            val day = binding.editTextDay.text.toString().trim()
            val month = binding.editTextMonth.text.toString().trim()
            val year = binding.editTextYear.text.toString().trim()
            val value = binding.editTextFinanceValue.text.toString().trim()
            val moviment = getMoviment

            if(name.isEmpty()){
                binding.editTextFinanceName.error = "Este campo é obrigatório"
                return@setOnClickListener
            }

            if(day.isEmpty()){
                binding.editTextDay.error = "Este campo é obrigatório"
                return@setOnClickListener
            }

            if(month.isEmpty()){
                binding.editTextMonth.error = "Este campo é obrigatório"
                return@setOnClickListener
            }

            if(year.isEmpty()){
                binding.editTextYear.error = "Este campo é obrigatório"
                return@setOnClickListener
            }

            if(value.isEmpty()){
                binding.editTextFinanceValue.error = "Este campo é obrigatório"
                return@setOnClickListener
            }

            if(moviment == null){
                binding.editTextFinanceValue.error = "Selecione o tipo de finança"
                return@setOnClickListener
            }

            val finance = Finance()
            finance.name = name
            finance.day = day
            finance.month = month
            finance.year = year
            finance.value = value
            finance.moviment = moviment

            viewModel.addFinance(finance)

        }

    }


    private fun getMoviment(){
        binding.chipGain.setOnClickListener {
            getMoviment = binding.chipGain.text.toString().trim()
            makeToast()
            changeBackgroundColor(true)
        }

        binding.chipLoss.setOnClickListener {
            getMoviment = binding.chipLoss.text.toString().trim()
            makeToast()
            changeBackgroundColor(false)
        }
    }

    private fun makeToast(){
        Toast.makeText(requireContext(), getMoviment, Toast.LENGTH_SHORT).show()
    }

    private fun changeBackgroundColor(isGain: Boolean){
        if (isGain){
            binding.buttonAddFinance.setBackgroundColor(resources.getColor(R.color.green))
        } else {
            binding.buttonAddFinance.setBackgroundColor(resources.getColor(R.color.red))
        }
    }

}