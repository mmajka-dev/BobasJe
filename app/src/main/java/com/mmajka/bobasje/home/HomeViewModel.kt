package com.mmajka.bobasje.home

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.bobasje.models.action
import com.mmajka.bobasje.models.child
import com.mmajka.bobasje.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val db = FirebaseDatabase.getInstance()
    val prefInstance = Preferences(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val ref = db.getReference(path!!).child("child")
    val refActions = db.getReference(path!!).child("actions")

    var _child = MutableLiveData<child>()
    val child: LiveData<child>
        get() = _child

    var _actions = MutableLiveData<ArrayList<action>>()
    val actions: LiveData<ArrayList<action>>
    get() = _actions

    fun getChild(name: TextView){
        ref.orderByChild("index").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                name.setText(snapshot.child("name").value.toString())
            }
        })
    }

    fun getCare(): LiveData<ArrayList<action>>{
        val items = ArrayList<action>()
    refActions.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear()
                for(i in snapshot.children) {
                    val item = i.getValue(action::class.java)
                    items.add(item!!)
                    _actions.value = items
                }
            }
        })
       return _actions
    }

    fun setActions(date: String, time: String, type: String, text: String){
        val action = action(date, time, type, text)
        refActions.child(Date().time.toString()).setValue(action)
    }

    fun clearList(){
        refActions.removeValue()
        _actions.value!!.clear()
    }
}