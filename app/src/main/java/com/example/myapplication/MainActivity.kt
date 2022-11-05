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
    data class Items(val id: Long, val value: Int)
    val dbHelper = DBHelper(this)
    private val list = mutableListOf<Items>()
    private lateinit var adapter: RecyclerAdapter
    lateinit var state: State
    class State(
        var EditTextMem: String,
        var ListMem: MutableList<Items> = mutableListOf(),
    ): Serializable
    companion object {
        const val STATE_KEY = "STATE"
        const val EXTRA_KEY = "EXTRA"
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val editText = findViewById<EditText>(R.id.editText)

        // сохраняем текущие значения в state
        state.EditTextMem = editText.text.toString()
        state.ListMem = list

        outState.putSerializable(STATE_KEY, state)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText: EditText = findViewById<EditText>(R.id.editText)

        adapter = RecyclerAdapter(list, {
            // адаптеру передали обработчик удаления элемента
            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }, {

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(EXTRA_KEY, list[it].value)
            startActivity(intent)
        })
        list.addAll(dbHelper.getAll())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if(editText.text.toString().isNotEmpty()){
                list.add(editText.text.toString())
                val id = dbHelper.add(editText.text.toString())
                editText.setText("")
                adapter.notifyItemInserted(list.lastIndex)

            }
        }
    }
}