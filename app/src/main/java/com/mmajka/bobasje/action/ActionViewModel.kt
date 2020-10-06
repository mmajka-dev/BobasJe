package com.mmajka.bobasje.action

import android.app.Application
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.mmajka.bobasje.R
import com.mmajka.bobasje.models.action
import com.mmajka.bobasje.utils.Preferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log
import kotlin.math.min
import kotlin.random.Random

class ActionViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Preferences(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val refActions = db.getReference(path!!).child("actions")

    fun getTime(): String{
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val time = if (minute < 10){
            "$hour:0$minute"
        }else{
            "$hour:$minute"
        }
        Log.i("TIME", "$time")
        return time
    }

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }

    fun setActions(date: String, time: String, type: String, text: String){
        val action = action(date, time, type, text)
        refActions.child(Date().time.toString()).setValue(action)
    }

    fun callTimePicker(context: Context, textView: TextView){
        val cal = Calendar.getInstance()
        var time = ""
        val builder = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            time = SimpleDateFormat("HH:mm").format(cal.time)
            textView.text = time
        }
        TimePickerDialog(context, builder, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }



}