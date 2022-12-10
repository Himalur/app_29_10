package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import layout.dialogYesOrNo
import java.lang.Exception

class ContactInfActivity : AppCompatActivity() {
    val dbHelper = DBHelper(this)
    companion object {
        const val EXTRA_KEY_ID = "id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactinf)
        var id = intent.getLongExtra(CreateOrChangeActivity.EXTRA_KEY_ID,-1L)
        val nameText = findViewById<TextView>(R.id.nameText)
        val surnameText = findViewById<TextView>(R.id.surnameText)
        val birthDateText = findViewById<TextView>(R.id.birthDateText)
        val phoneNumberText = findViewById<TextView>(R.id.phoneNumberText)

        if(id == -1L){
            id = intent.getLongExtra(MainActivity.EXTRA_KEY_ID,-2L)
            if(id==-2L) {
                throw Exception("Error: Incorrect EXTRA")
            }
            else{
                val contact = dbHelper.getById(id)
                nameText.text= contact!!.name
                surnameText.text= contact!!.surname
                birthDateText.text= contact!!.birthDate
                phoneNumberText.text= contact!!.phoneNumber
            }
        }
        else{
            val contact = dbHelper.getById(id)
            nameText.text= contact!!.name
            surnameText.text= contact!!.surname
            birthDateText.text= contact!!.birthDate
            phoneNumberText.text= contact!!.phoneNumber
        }
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            dialogYesOrNo(
                this,
                "Вопрос",
                "Вы действительно хотите удалить контакт?",
                DialogInterface.OnClickListener { _, _ ->
                    dbHelper.remove(id)
                    finish()
                })
        }
        val changeButton = findViewById<Button>(R.id.changeButton)
        changeButton.setOnClickListener {
            val intent = Intent(this, CreateOrChangeActivity::class.java)
            intent.putExtra(EXTRA_KEY_ID, id)
            startActivity(intent)
            finish()
        }
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

    }
}