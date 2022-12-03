package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
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

        val button = findViewById<Button>(R.id.listFindButton)
        button.setOnClickListener {
            /*if(editText.text.toString().isNotEmpty()){
                val id = dbHelper.add(editText.text.toString())
                val contact = Contact(
                    id,
                    editText.text.toString(),
                )
                list.add(contact)
                editText.setText("")
                adapter.notifyItemInserted(list.lastIndex)

            }*/

        }
    }

    fun okClicked(id : Long) {
        dbHelper.remove(id)
        list.remove(list.find { it.id == id})

    }
}
