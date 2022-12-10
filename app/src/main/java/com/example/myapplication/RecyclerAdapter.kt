package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(

    // передаём коллбек нажатия на кнопку
    private val contactClick: (id: Int) -> Unit,
    private val deleteClick: (id: Int) -> Unit,
    private val settingsClick: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private val list: MutableList<Contact> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }


    fun updateList(newList: List<Contact>) {
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].phoneNumber
        // обработчик нажатия кнопки
        holder.textView.setOnClickListener(){
            contactClick(holder.adapterPosition)
        }
        holder.deleteButton.setOnClickListener {
            deleteClick(holder.adapterPosition)
        }
        holder.settingsButton.setOnClickListener {
            settingsClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById<TextView>(R.id.textView)
        // находим кнопку
        val deleteButton = itemView.findViewById<TextView>(R.id.deleteButton)
        val settingsButton = itemView.findViewById<Button>(R.id.listSettingsButton)
    }

}

