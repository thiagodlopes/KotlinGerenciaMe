package com.thdlopes.kotlingerenciame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.data.FinanceViewModel
import com.thdlopes.kotlingerenciame.databinding.FragmentUpdateFinanceDialogBinding

class UpdateFinanceDialogFragment(private val finance: Finance) : DialogFragment() {

    private var _binding: FragmentUpdateFinanceDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var getMoviment: String

    private lateinit var viewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateFinanceDialogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(FinanceViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getMoviment = finance.moviment.toString()

        setMoviment()
        getMoviment()

        binding.editTextFinanceName.setText(finance.name)
        binding.editTextDay.setText(finance.day)
        binding.editTextMonth.setText(finance.month)
        binding.editTextYear.setText(finance.year)
        binding.editTextFinanceValue.setText(finance.value)

        binding.buttonUpdateFinance.setOnClickListener {
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

            finance.name = name
            finance.day = day
            finance.month = month
            finance.year = year
            finance.value = value
            finance.moviment = moviment

            viewModel.updateFinance(finance)
            dismiss()
            Toast.makeText(context, "Finança alterada", Toast.LENGTH_SHORT).show()

        }

    }

    fun setMoviment(){
        if (getMoviment == "Ganho"){
            binding.chipGain.isChecked = true
            changeBackgroundColor(true)
        } else {
            binding.chipLoss.isChecked = true
            changeBackgroundColor(false)
        }
    }

    fun getMoviment(){
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

    fun makeToast(){
        Toast.makeText(requireContext(), getMoviment, Toast.LENGTH_SHORT).show()
    }

    fun changeBackgroundColor(isGain: Boolean){
        if (isGain){
            binding.buttonUpdateFinance.setBackgroundColor(resources.getColor(R.color.green))
        } else {
            binding.buttonUpdateFinance.setBackgroundColor(resources.getColor(R.color.red))
        }
    }


}