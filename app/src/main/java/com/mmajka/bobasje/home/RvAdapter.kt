package com.mmajka.bobasje.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.bobasje.R
import com.mmajka.bobasje.models.action
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.single_rv_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class RvAdapter(var actions: ArrayList<action>): RecyclerView.Adapter<RvHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_rv_item, parent, false)
        return RvHolder(view)
    }

    override fun getItemCount(): Int {
        return actions.size
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        val bind = actions.get(position)
        val image = holder.image
        val card = holder.card
        holder.bind(bind)
        holder.onDateCheck(bind)
        when(actions.get(position).type){
            "Diaper" -> {
                image.setImageResource(R.drawable.ic_diaper_rv)
                card.setCardBackgroundColor(Color.parseColor("#6DB67F"))
            }
            "Feeding" -> {
                image.setImageResource(R.drawable.ic_bottle_rv)
                card.setCardBackgroundColor(Color.parseColor("#62BCD4"))
            }
            "Bath" ->{
                image.setImageResource(R.drawable.ic_bathtub_rv)
                card.setCardBackgroundColor(Color.parseColor("#FA8997"))
            }
            "Play time" -> {
                image.setImageResource(R.drawable.ic_play_time_rv)
                card.setCardBackgroundColor(Color.parseColor("#F2C06C"))
            }
            "Sleep" -> {
                image.setImageResource(R.drawable.ic_sleep_rv)
                card.setCardBackgroundColor(Color.parseColor("#CAA4CC"))
            }
        }
    }
}

class RvHolder(view: View): RecyclerView.ViewHolder(view){
    val image = itemView.imageView2
    val card = itemView.card
    val date = itemView.date
    val time = itemView.time
    val title = itemView.title
    val details = itemView.details

    fun bind(action: action){
        date.text = action.date
        time.text = action.time
        title.text = action.type
        details.text = action.text
    }

    fun onDateCheck(action: action){
        if(action.date.equals(getDate())){
            date.text = "Today"
        }
    }

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }
}