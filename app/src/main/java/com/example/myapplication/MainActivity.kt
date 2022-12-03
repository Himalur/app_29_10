package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.dialogYesOrNo


class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
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
            dialogYesOrNo(
                this,
                "Вопрос",
                "Вы перестали пить коньяк по утрам?",
                DialogInterface.OnClickListener { dialog, id ->
                    dbHelper.remove(list[it].id)
                    list.removeAt(it)
                })
        }
        ) {

            val intent = Intent(this, activity_contactinf::class.java)
            intent.putExtra(EXTRA_KEY, it)
            startActivity(intent)
        }
        list.addAll(dbHelper.getAll())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, activity_CRorCH::class.java)
            intent.putExtra(EXTRA_KEY, -1)
            startActivity(intent)
        }
    }

    fun okClicked(id : Long) {
        dbHelper.remove(id)
        list.remove(list.find { it.id == id})

    }

}
