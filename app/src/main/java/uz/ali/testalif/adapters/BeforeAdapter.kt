package uz.ali.testalif.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.testalif.R
import uz.ali.testalif.dialogs.DialogBefore


class BeforeAdapter(var list: List<Long>,val listenner:DialogBefore) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_before, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.tvName.text = "За ${list[position]} минут до"

            holder.itemView.setOnClickListener {
                listenner.setData(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(iteView: View) : RecyclerView.ViewHolder(iteView) {
        var tvName:TextView=iteView.findViewById(R.id.titleItemBefore)
    }
}