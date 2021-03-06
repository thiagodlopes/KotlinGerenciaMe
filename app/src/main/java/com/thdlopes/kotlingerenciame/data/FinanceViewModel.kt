package com.thdlopes.kotlingerenciame.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thdlopes.kotlingerenciame.data.NODE_FINANCES
import java.lang.Exception

class FinanceViewModel: ViewModel() {

    private  lateinit var firebaseAuth: FirebaseAuth

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

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val finance = snapshot.getValue(Finance::class.java)
            finance?.id = snapshot.key
            finance?.isDeleted = true
            _finance.value = finance!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    fun getRealTimeUpdate(){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser?.uid.toString()

        var query :Query = FirebaseDatabase.getInstance().getReference("finances").orderByChild("userId").equalTo( firebaseUser)
        query.addChildEventListener(childEventListener)
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

    fun deleteFinance(finance: Finance){
        dbfinances.child(finance.id!!).setValue(null)
            .addOnCompleteListener {
                if(it.isSuccessful){
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