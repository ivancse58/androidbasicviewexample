package com.example.ivan.viewsample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate enter")
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart enter")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume enter")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart enter")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause enter")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop enter")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy enter")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}