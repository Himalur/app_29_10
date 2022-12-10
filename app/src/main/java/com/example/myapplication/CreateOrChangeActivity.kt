package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Exception

class CreateOrChangeActivity : AppCompatActivity() {
    val dbHelper = DBHelper(this)

    companion object {
        const val EXTRA_KEY_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createorchange)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val nameEditText = findViewById<TextView>(R.id.nameEditText)
        val surnameEditText = findViewById<TextView>(R.id.surnameEditText)
        val phoneNumberEditText = findViewById<TextView>(R.id.phoneNumberEditText)
        val birthDateEditText = findViewById<TextView>(R.id.birthDateEditText)


        cancelButton.setOnClickListener {
            finish()
        }
        var n = intent.getLongExtra(MainActivity.EXTRA_KEY_ID, 0L)

        if (n == 0L) {
            saveButton.setOnClickListener {
                createContact(
                    nameEditText.text.toString(),
                    surnameEditText.text.toString(),
                    birthDateEditText.text.toString(),
                    phoneNumberEditText.text.toString()
                )
            }
        } else {
            n = intent.getLongExtra(ContactInfActivity.EXTRA_KEY_ID, -1L)
            if (n == -1L) {
                throw Exception("Error: Incorrect Extra")
            } else {
                val contact = dbHelper.getById(n)
                nameEditText.text= contact!!.name
                surnameEditText.text= contact!!.surname
                birthDateEditText.text= contact!!.birthDate
                phoneNumberEditText.text= contact!!.phoneNumber

                saveButton.setOnClickListener {
                    updateDB(
                        n,
                        nameEditText.text.toString(),
                        surnameEditText.text.toString(),
                        birthDateEditText.text.toString(),
                        phoneNumberEditText.text.toString()
                    )
                }
            }
        }
    }

    fun createContact(name: String, surname: String, birthDate: String, phoneNumber: String) {

        var id = dbHelper.add(name, surname, birthDate, phoneNumber)
        val intent = Intent(this, ContactInfActivity::class.java)
        intent.putExtra(EXTRA_KEY_ID, id)
        startActivity(intent)


        finish()

    }

    fun updateDB(id: Long, name: String, surname: String, birthDate: String, phoneNumber: String) {
        dbHelper.update(id, name, surname, birthDate, phoneNumber)

        finish()
    }
}