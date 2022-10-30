package com.example.storage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MyAdapter(private val context: Context, val list: ArrayList<Person>) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.row_view, p2, false)
        val person = list[p0]
        val name = rowView.findViewById<TextView>(R.id.tv_name)
        val age = rowView.findViewById<TextView>(R.id.tv_age)

        name.text = person.name
        age.text = person.age.toString()

        val deleteButton = rowView.findViewById<ImageButton>(R.id.ib_delete)
        deleteButton.setOnClickListener {
            val db = SQLiteDBHelper(context, null)
            if (db.delete(person.name)) {
                Toast.makeText(context, "${person.name} successfully got deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Some error occurred during deletion", Toast.LENGTH_SHORT)
            }
        }

        val updateButton = rowView.findViewById<ImageButton>(R.id.ib_update)
        updateButton.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("name", person.name)
            intent.putExtra("age", person.age)
            context.startActivity(intent)
        }
        return rowView
    }
}