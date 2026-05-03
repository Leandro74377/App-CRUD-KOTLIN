package com.example.appcrudkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var rvItems: RecyclerView

    private val items = mutableListOf<Item>()
    private lateinit var adapter: ItemAdapter
    private var nextId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etDescription = findViewById(R.id.etDescription)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        rvItems = findViewById(R.id.rvItems)

        adapter = ItemAdapter(items)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = adapter

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val description = etDescription.text.toString()
            if (name.isNotEmpty() && description.isNotEmpty()) {
                items.add(Item(nextId++, name, description))
                adapter.notifyDataSetChanged()
                clearFields()
                Toast.makeText(this, "Item agregado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val name = etName.text.toString()
            val description = etDescription.text.toString()
            val item = items.find { it.name == name }
            if (item != null && description.isNotEmpty()) {
                val index = items.indexOf(item)
                items[index] = item.copy(description = description)
                adapter.notifyDataSetChanged()
                clearFields()
                Toast.makeText(this, "Item actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Item no encontrado o descripción vacía", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val name = etName.text.toString()
            val item = items.find { it.name == name }
            if (item != null) {
                items.remove(item)
                adapter.notifyDataSetChanged()
                clearFields()
                Toast.makeText(this, "Item eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Item no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        etName.text.clear()
        etDescription.text.clear()
    }
}