package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.dialogYesOrNo
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    private val list = mutableListOf<Contact>()
    private lateinit var adapter: RecyclerAdapter
    companion object {
        const val EXTRA_KEY_ID = "id"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText: EditText = findViewById<EditText>(R.id.editText)
        adapter = RecyclerAdapter(
            {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${list[it].phoneNumber}"));
                startActivity(intent);
            },
            {
                dialogYesOrNo(
                    this,
                    "Вопрос",
                    "Вы действительно хотите удалить контакт?",
                    DialogInterface.OnClickListener { _, _ ->
                    dbHelper.remove(list[it].id)
                    list.removeAt(it)
                    editText.text.clear()
                    adapter.updateList(list)
                })
            },
            {

                val intent = Intent(this, ContactInfActivity::class.java)
                intent.putExtra(EXTRA_KEY_ID, list[it].id)
                startActivity(intent)
            }
        )

        editText.addTextChangedListener {
            if (editText.text.isBlank()){
                adapter.updateList(list)


            }
            else {
                val filteredList = list.filter {
                    it.name.contains(
                        editText.text.toString(),
                        true
                    )
                } as MutableList<Contact>
                adapter.updateList(filteredList)
            }
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
        adapter.updateList(list)
        adapter.notifyDataSetChanged()
    }
}
