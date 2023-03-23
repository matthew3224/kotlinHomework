package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var str: EditText
    lateinit var res: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        str = findViewById(R.id.edit_message)
        res = findViewById(R.id.textField)
        findViewById<Button>(R.id.send).setOnClickListener {
            res.text = str.text
        }
    }

    override fun onStart() {
        super.onStart()
    }

}