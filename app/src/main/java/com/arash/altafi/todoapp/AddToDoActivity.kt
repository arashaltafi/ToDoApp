package com.arash.altafi.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class AddToDoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID: String = "EXTRA_ID"
        const val EXTRA_TITLE: String = "EXTRA_TITLE"
        const val EXTRA_DESC: String = "EXTRA_DESC"
    }

    private lateinit var btnAdd: MaterialButton
    private lateinit var edtTitle: EditText
    private lateinit var edtDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        init()
    }

    private fun init() {
        bindViews()

        val inputIntent: Intent = intent
        if (inputIntent.hasExtra(EXTRA_ID)) {
            title = "Edit ToDo"
            edtTitle.setText(inputIntent.getStringExtra(EXTRA_TITLE))
            edtDescription.setText(inputIntent.getStringExtra(EXTRA_DESC))
            btnAdd.text = "Update Item"
        } else {
            title = "Add ToDo"
            btnAdd.text = "Add New Item"
        }

        btnAdd.setOnClickListener {
            val title = edtTitle.text.toString().trim()
            val description = edtDescription.text.toString().trim()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill title and description", Toast.LENGTH_SHORT).show()
            } else {
                val data = Intent()
                val id = intent.getIntExtra(EXTRA_ID, -1)
                if (id != -1) {
                    data.putExtra(EXTRA_ID, id)
                }
                data.putExtra(EXTRA_TITLE, title)
                data.putExtra(EXTRA_DESC, description)
                setResult(RESULT_OK, data)
                finish()
            }
        }

    }

    private fun bindViews() {
        btnAdd = findViewById(R.id.btn_add)
        edtDescription = findViewById(R.id.edt_description)
        edtTitle = findViewById(R.id.edt_title)
    }
}