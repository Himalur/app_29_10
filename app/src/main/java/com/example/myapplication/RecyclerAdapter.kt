package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(
    private val list: List<String>,
    // передаём коллбек нажатия на кнопку
    private val onItemClick: (id: Int) -> Unit,
    private val onItemClick2: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
        // обработчик нажатия кнопки
        holder.buttonAdd.setOnClickListener {
            onItemClick(holder.adapterPosition)
        }
        holder.buttonAdd2.setOnClickListener {
            onItemClick2(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById<TextView>(R.id.textView)

        // находим кнопку
        val buttonAdd = itemView.findViewById<TextView>(R.id.button)
        val buttonAdd2 = itemView.findViewById<Button>(R.id.button2)
    }

}

