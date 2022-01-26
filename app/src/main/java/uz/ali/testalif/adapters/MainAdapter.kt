package uz.ali.testalif.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsm.roomdaggerhilt.db.TaskEntity
import uz.ali.testalif.R
import uz.ali.testalif.fragments.main.FragmentMain


class MainAdapter(var list: List<TaskEntity>, var mainFragment:FragmentMain, var checkPos:Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            if (list[position].status=="true" && position==0){
                holder.itemView.setBackgroundResource(R.drawable.shape_main_start)
                holder.checkbox.isChecked=true
                holder.tvName.setTextColor(holder.itemView.resources.getColor(R.color.white))
                holder.checkbox.buttonTintList=holder.itemView.resources.getColorStateList(R.color.white)
            }else if (list[position].status=="true" && position!=checkPos){
                holder.itemView.setBackgroundResource(R.drawable.shape_main_center)
                holder.checkbox.isChecked=true
                holder.tvName.setTextColor(holder.itemView.resources.getColor(R.color.white))
                holder.checkbox.buttonTintList=holder.itemView.resources.getColorStateList(R.color.white)
            }else if (list[position].status=="true" && position==checkPos){
                holder.itemView.setBackgroundResource(R.drawable.shape_main_end)
                holder.checkbox.isChecked=true
                holder.tvName.setTextColor(holder.itemView.resources.getColor(R.color.white))
                holder.checkbox.buttonTintList=holder.itemView.resources.getColorStateList(R.color.white)
            }else{
                holder.itemView.setBackgroundResource(R.drawable.shape_create_not)
                holder.checkbox.isChecked=false
                holder.imageItem.setBackgroundResource(R.drawable.ic_baseline_black_chevron_right_24)
            }

            holder.tvName.text = list[position].name
            holder.tvTime.text = "${list[position].time}  | Будильник За ${list[position].alarmTime} минут до"


            holder.checkbox.setOnClickListener {
                mainFragment.setItemChekClick(list[position].id,holder.checkbox.isChecked)
            }
            holder.itemView.setOnLongClickListener {
                mainFragment.setOnLongClick(list[position].id)
                return@setOnLongClickListener true
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(iteView: View) : RecyclerView.ViewHolder(iteView) {
        var tvName:TextView=iteView.findViewById(R.id.titleItem)
        var tvTime:TextView=iteView.findViewById(R.id.timeItem)
        var checkbox:CheckBox=iteView.findViewById(R.id.checkbox)
        var imageItem:ImageView=iteView.findViewById(R.id.imageItem)
    }
}