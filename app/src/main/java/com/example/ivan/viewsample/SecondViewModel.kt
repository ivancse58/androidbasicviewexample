package com.example.ivan.viewsample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SecondViewModel : ViewModel() {
    private lateinit var job: CompletableJob
    val toastObs = MutableLiveData<String>()
    val updateProgressObs = MutableLiveData<Int>()
    val completeTxtObs = MutableLiveData<String>()
    val btnTxtObs = MutableLiveData<String>()

    init {
        Log.d(TAG, "ViewModel init!")
    }

    fun initLongRunningTask() {
        Log.d(TAG, "longRunningTask enter!")
        cancelJob()
        initJob()

        btnTxtObs.value = "Cancel Job #1"
        viewModelScope.launch(IO + job) {
            Log.d(TAG, "coroutine $this is activated with job $job.")

            for (i in PROGRESS_START..PROGRESS_MAX) {
                delay((JOB_TIME / PROGRESS_MAX))
                // set value to invoke main thread
                withContext(Main) {
                    updateProgressObs.value = i
                }
            }

            // set value to invoke main thread
            withContext(Main) {
                completeTxtObs.value = "Job is complete!"
                btnTxtObs.value = "Job finished!"
            }
        }
    }

    fun cancelJob() {
        if (!::job.isInitialized) return
        job.cancel()
    }

    fun resetJob() {
        cancelJob()
        initJob()
        btnTxtObs.value = "Restart Job!"
    }

    private fun initJob() {
        job = Job()
        viewModelScope.launch(Main) {
            updateProgressObs.value = 0
        }
        job.invokeOnCompletion {
            it?.message.let { data ->
                var msg = data
                if (msg.isNullOrBlank()) {
                    msg = "Unknown cancellation error."
                }
                viewModelScope.launch(Main) {
                    toastObs.value = msg.orEmpty()
                }
                Log.e(TAG, "$job was cancelled. Reason: $msg")
            }
        }
    }

    companion object {
        private const val JOB_TIME = 10000L // 10s
        private const val PROGRESS_MAX = 100
        private const val PROGRESS_START = 0
        private const val TAG = "SecondViewModel--------"
    }
}