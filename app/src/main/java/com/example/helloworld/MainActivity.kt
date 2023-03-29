package com.example.helloworld

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {
    lateinit var str: EditText
    lateinit var res: TextView
    companion object {
        var staticfield: String = ""
    }

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    //ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        str = findViewById(R.id.edit_message)
        res = findViewById(R.id.textField)
        res.text = staticfield
        findViewById<Button>(R.id.send).setOnClickListener {
            staticfield = str.text.toString()
            res.text = staticfield
            val questionTextResId = quizViewModel
        }
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
    }

    interface UmoriliApi {
        @GET("/api/get")
        fun getData(
            @Query("name") resourceName: String?,
            @Query("num") count: Int
        ): Call<List<PostModel?>?>?
    }

}

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    var currentIndex = 0
    private val questionBank = R.id.textField
    init {
        Log.d(TAG, "ViewModel instance created")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}

class PostModel {

}
