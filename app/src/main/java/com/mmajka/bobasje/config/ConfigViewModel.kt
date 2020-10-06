package com.mmajka.bobasje.config

import android.app.Application
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.bobasje.models.child
import com.mmajka.bobasje.utils.Preferences
import kotlin.random.Random


class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    val db = FirebaseDatabase.getInstance()
    val prefInstance = Preferences(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val ref = db.getReference(path!!).child("child")

    fun setValue(name: String){
        val child = child(name)
        ref.setValue(child)
    }






}