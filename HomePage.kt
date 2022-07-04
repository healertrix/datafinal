package com.example.blogdataviz

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {
    lateinit var button: Button
    lateinit var spinner:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this,R.array.csv,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter
        button = findViewById<Button>(R.id.pageswitch)
        button.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            val message: String = spinner.selectedItem.toString()
            intent.putExtra("message", message);

            startActivity(intent)
        }

    }
}