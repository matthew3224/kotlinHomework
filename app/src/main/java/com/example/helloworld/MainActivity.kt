package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.helloworld.retrofit.ProductApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {
    lateinit var str: EditText
    lateinit var res: TextView

    companion object {
        var staticfield: String = ""
    }

    //    private val quizViewModel: QuizViewModel by viewModel()
    //ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        str = findViewById(R.id.edit_message)
        res = findViewById(R.id.textField)
//        res.text = staticfield

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val productApi = retrofit.create(ProductApi::class.java)

        findViewById<Button>(R.id.send).setOnClickListener {
//            staticfield = str.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(str.text.toString().toInt())
                runOnUiThread{
                    res.text = product.title
                }
            }
//            res.text = staticfield
        }

//        val provider: ViewModelProvider = ViewModelProviders.of(this)
//        val quizViewModel = provider.get(QuizViewModel::class.java)
    }


}

class PostModel {

}
