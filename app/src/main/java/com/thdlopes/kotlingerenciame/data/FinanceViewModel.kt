package com.thdlopes.kotlingerenciame.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thdlopes.kotlingerenciame.data.NODE_FINANCES
import java.lang.Exception
import com.google.firebase.database.FirebaseDatabase

class FinanceViewModel: ViewModel() {

    private val dbfinances = FirebaseDatabase.getInstance().getReference(NODE_FINANCES)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    fun addFinance(finance: Finance){
        finance.id = dbfinances.push().key

        dbfinances.child(finance.id!!).setValue(finance).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            } else{
                _result.value = it.exception
            }

        }
    }


}