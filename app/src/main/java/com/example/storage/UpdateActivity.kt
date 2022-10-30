package com.example.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val nameEditText = findViewById<EditText>(R.id.et_name)
        val ageEditText = findViewById<EditText>(R.id.et_age)
        val button = findViewById<Button>(R.id.btn_update)

        val name = intent.getStringExtra("name").toString()
        val age = intent.getIntExtra("age", 0).toString()
        nameEditText.setText(name)
        ageEditText.setText(age)

        button.setOnClickListener {
            val db = SQLiteDBHelper(this, null)
            db.update(name, nameEditText.text.toString(), ageEditText.text.toString())
            finish()
        }
    }
}