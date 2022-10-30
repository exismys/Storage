package com.example.storage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SQLiteExample : AppCompatActivity() {
    @SuppressLint("Range")
    private lateinit var adapter: MyAdapter

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite_example)

        val addName: Button = findViewById(R.id.btn_addName)
        val printName: Button = findViewById(R.id.btn_printName)
        val delAll: Button= findViewById(R.id.btn_deleteAll)
        val enterName: EditText = findViewById(R.id.et_name)
        val enterAge: EditText = findViewById(R.id.et_age)
        val nameAgeListView: ListView = findViewById(R.id.lv_nameAgeList)

        val personList: ArrayList<Person> = ArrayList()

        addName.setOnClickListener {
            val db = SQLiteDBHelper(this, null)
            val name = enterName.text.toString()
            val age = enterAge.text.toString()

            db.addName(name, age)

            Toast.makeText(this, "$name added to database", Toast.LENGTH_LONG).show()

            enterName.text.clear()
            enterAge.text.clear()
        }

        printName.setOnClickListener {
            val db = SQLiteDBHelper(this, null)
            val cursor = db.getName()

            if (cursor?.count == 0) {
                Toast.makeText(this, "No data in the database to print", Toast.LENGTH_LONG).show()
            } else {
                cursor!!.moveToFirst()
                personList.clear()
                while (cursor.moveToNext()) {
                    personList.add(
                        Person(cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.NAME_COl)), cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.AGE_COL)))
                    )
                }
                adapter = MyAdapter(this@SQLiteExample, personList)
                nameAgeListView.adapter = adapter
                cursor.close()
            }
        }

        delAll.setOnClickListener {
            val db = SQLiteDBHelper(this, null)
            db.deleteAll()
            adapter.list.clear()
            adapter.notifyDataSetChanged()
        }
    }
}