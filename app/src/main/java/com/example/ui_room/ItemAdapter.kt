package com.example.ui_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val onItemClickListener:(ToDo)->Unit):ListAdapter<ToDo, ItemAdapter.ItemHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.ui_list_item,
            parent,false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(getItem(position))
        {
            holder.title.text=Title
            holder.done.isChecked=done
        }
    }

    fun getItemAt(position: Int): ToDo =getItem(position)

    inner class ItemHolder(v: View): RecyclerView.ViewHolder(v)
    {
        val title: TextView =itemView.findViewById(R.id.ui_list_title)
        val done:CheckBox=itemView.findViewById(R.id.ui_item_done)
        init {
            itemView.setOnClickListener {
                if(adapterPosition!= DiffUtil.DiffResult.NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }
    }
}
private val diffCallback = object : DiffUtil.ItemCallback<ToDo>()
{
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo) = oldItem.id==newItem.id

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo)=
        oldItem.Title==newItem.Title
                && oldItem.done==newItem.done
}