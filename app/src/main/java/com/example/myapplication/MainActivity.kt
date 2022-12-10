package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.dialogYesOrNo


class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    private val list = mutableListOf<Contact>()
    var filteredList = list
    private lateinit var adapter: RecyclerAdapter
    companion object {
        const val EXTRA_KEY_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText: EditText = findViewById<EditText>(R.id.editText)
        adapter = RecyclerAdapter(filteredList, {
            dialogYesOrNo(
                this,
                "Вопрос",
                "Вы действительно хотите удалить контакт?",
                DialogInterface.OnClickListener { _, _ ->
                    dbHelper.remove(list[it].id)
                    list.removeAt(it)
                    filteredList=list
                    editText.text.clear()
                    adapter.notifyDataSetChanged()
                })
        }
        ) {

            val intent = Intent(this, ContactInfActivity::class.java)
            intent.putExtra(EXTRA_KEY_ID, list[it].id)
            startActivity(intent)
        }
        editText.doAfterTextChanged {
            if (editText.text.isEmpty()){
                filteredList = list


            }
            else {
                filteredList = list.filter {
                    it.name.contains(
                        editText.text.toString(),
                        true
                    )
                } as MutableList<Contact>

            }
            adapter.notifyDataSetChanged()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, CreateOrChangeActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart(){
        super.onStart()
        val editText: EditText = findViewById<EditText>(R.id.editText)
        editText.text.clear()
        list.clear()
        list.addAll(dbHelper.getAll())
        //adapter.updateList(filteredList)
        adapter.notifyDataSetChanged()
    }
}
