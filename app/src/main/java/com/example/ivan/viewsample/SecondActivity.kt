package com.example.ivan.viewsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

@SuppressLint("SetTextI18n")
class SecondActivity : AppCompatActivity() {
    private lateinit var jobButton: Button
    private lateinit var jobProgressBar: ProgressBar
    private lateinit var jobCompleteText: TextView

    private val model: SecondViewModel by lazy {
        ViewModelProvider(this)[SecondViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate enter")
        setContentView(R.layout.activity_secord)
        supportActionBar?.title = "Job Example View";
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);


        jobButton = findViewById(R.id.job_button)
        jobProgressBar = findViewById(R.id.job_progress_bar)
        jobCompleteText = findViewById(R.id.job_complete_text)

        jobButton.setOnClickListener {
            if (jobProgressBar.progress > 0) {
                Log.d(TAG, "Progress is running!")
                model.resetJob()
            } else {
                Log.d(TAG, "Progress is zero")
                model.initLongRunningTask()
            }
        }

        model.btnTxtObs.observe(this, {
            jobButton.text = it
        })

        model.toastObs.observe(this, {
            showToast(it)
        })
        model.updateProgressObs.observe(this, {
            jobProgressBar.progress = it
        })
        model.completeTxtObs.observe(this, {
            updateJobCompleteTextView(it)
        })
    }

    private fun updateJobCompleteTextView(text: String) {
        jobCompleteText.text = text
    }

    private fun showToast(text: String) {
        Toast.makeText(this@SecondActivity, text, Toast.LENGTH_SHORT).show()
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
        model.cancelJob()
        Log.d(TAG, "onDestroy enter")
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "SecondActivity------"
    }
}