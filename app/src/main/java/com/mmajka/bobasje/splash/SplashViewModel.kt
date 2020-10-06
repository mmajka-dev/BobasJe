package com.mmajka.bobasje.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class SplashViewModel : ViewModel() {
    val db = FirebaseDatabase.getInstance()
    val ref = db.getReference("child")

    var _isExist = MutableLiveData<Boolean>()
    val isExist: LiveData<Boolean>
        get() = _isExist

    fun getChild(){
        ref.orderByChild("index").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("name")) {
                    onExist()
                }else{
                    onConfig()
                }
            }
        })
    }

    fun onExist(){
        _isExist.value = true
        Log.i("isExist", "${_isExist.value}")
    }

    fun onConfig(){
        _isExist.value = false
    }

    fun generateID(): String{
        val id = Random.nextInt(100000, 999999)
        Log.i("ID", "$id")
        return id.toString()
    }
}