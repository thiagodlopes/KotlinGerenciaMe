package com.thdlopes.kotlingerenciame.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.thdlopes.kotlingerenciame.data.NODE_FINANCES
import java.lang.Exception
import com.google.firebase.database.FirebaseDatabase

class FinanceViewModel: ViewModel() {

    private val dbfinances = FirebaseDatabase.getInstance().getReference(NODE_FINANCES)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _finance = MutableLiveData<Finance>()
    val finance: LiveData<Finance> get() = _finance

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

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val finance = snapshot.getValue(Finance::class.java)
            finance?.id = snapshot.key
            _finance.value = finance!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val finance = snapshot.getValue(Finance::class.java)
            finance?.id = snapshot.key
            _finance.value = finance!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    fun getRealTimeUpdate(){
        dbfinances.addChildEventListener(childEventListener)
    }

    fun updateFinance(finance: Finance){
        dbfinances.child(finance.id!!).setValue(finance)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        dbfinances.removeEventListener(childEventListener)
    }

}