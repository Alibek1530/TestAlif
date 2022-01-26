package uz.ali.testalif.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsm.roomdaggerhilt.db.TaskEntity
import uz.ali.testalif.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class ItemsAdapter(var list: List<TaskEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rec, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {

            holder.checkbox.isChecked = list[position].status=="true"
            holder.dateItem.text=setDateFormat(list[position].date)
            holder.tvName.text = list[position].name
            holder.tvTime.text = "${list[position].time}  | Будильник За ${list[position].alarmTime} минут до"
        }
    }

    fun setDateFormat(calendarDate: String): String {
        val spf = SimpleDateFormat("yyyy-MM-dd", Locale("RU"))
        val newDate: Date = spf.parse(calendarDate)
        val spf1 = SimpleDateFormat("dd MMMM", Locale("RU"))
        return spf1.format(newDate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(iteView: View) : RecyclerView.ViewHolder(iteView) {
        var tvName:TextView=iteView.findViewById(R.id.titleItem)
        var tvTime:TextView=iteView.findViewById(R.id.timeItem)
        var checkbox:CheckBox=iteView.findViewById(R.id.checkbox)
        var dateItem:TextView=iteView.findViewById(R.id.dateItem)
    }
}