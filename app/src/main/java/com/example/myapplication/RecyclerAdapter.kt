package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(
    private val list: MutableList<Contact>,
    // передаём коллбек нажатия на кнопку
    private val deleteClick: (id: Int) -> Unit,
    private val settingsClick: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].surname + list[position].name
        // обработчик нажатия кнопки
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
        val deleteButton = itemView.findViewById<TextView>(R.id.addButton)
        val settingsButton = itemView.findViewById<Button>(R.id.listSettingsButton)
    }

}

