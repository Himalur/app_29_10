package com.example.myapplication

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import kotlin.collections.mutableListOf

class MainActivity : AppCompatActivity() {
    val dbHelper = DBHelper(this)
    private val list = mutableListOf<Contact>()
    private lateinit var adapter: RecyclerAdapter
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText: EditText = findViewById<EditText>(R.id.editText)

        adapter = RecyclerAdapter(list, {
            // адаптеру передали обработчик удаления элемента
            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }) {

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(EXTRA_KEY, list[it].name)
            startActivity(intent)
        }
        list.addAll(dbHelper.getAll())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if(editText.text.toString().isNotEmpty()){
                val id = dbHelper.add(editText.text.toString())
                val contact = Contact(
                    id,
                    editText.text.toString()
                )
                list.add(contact)
                editText.setText("")
                adapter.notifyItemInserted(list.lastIndex)

            }
        }
    }
}