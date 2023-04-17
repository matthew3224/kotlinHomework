package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helloworld.retrofit.Gist
import com.example.helloworld.retrofit.GithubAPI
//import com.example.helloworld.retrofit.ProductApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


//class MainActivity : AppCompatActivity() {
//    lateinit var str: EditText
//    private lateinit var res: TextView
//
//    private lateinit var viewModel: MyViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        str = findViewById(R.id.edit_message)
//        res = findViewById(R.id.textField)
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(MoshiConverterFactory.create()).build()
//
//        val productApi = retrofit.create(ProductApi::class.java)
//
//        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
//        res.text = viewModel.getData()
//
//        findViewById<Button>(R.id.send).setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val product = productApi.getProductById(str.text.toString().toInt())
//                runOnUiThread {
//                    res.text = product.title
//                    viewModel.setData(res.text.toString())
//                }
//            }
//        }
//
//    }
//
//
//}

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_message)
        textView = findViewById(R.id.textField)
        button = findViewById(R.id.send)

        button.setOnClickListener { getGist() }
    }

    private fun getGist() {
        val gistId = editText.text.toString()

        val service = GithubService()
        service.getGist(gistId, object : retrofit2.Callback<Gist> {
            override fun onResponse(call: Call<Gist>, response: Response<Gist>) {
                if (response.isSuccessful) {
                    val gist = response.body()
                    textView.text = "Gist content: ${gist?.files?.values?.first()?.content}"
                }
            }

            override fun onFailure(call: Call<Gist>, t: Throwable) {
                textView.text = "Error: ${t.message}"
            }
        })
    }
}

class GithubService {

    private val api: GithubAPI

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(GithubAPI::class.java)
    }

    fun getGist(gistId: String, callback: retrofit2.Callback<Gist>) {
        api.getGist(gistId).enqueue(callback)
    }
}
class MyViewModel : ViewModel() {
    private var data: MutableLiveData<String> = MutableLiveData()

    fun setData(data: String) {
        this.data.value = data
    }

    fun getData(): String? {
        return data.value
    }
}
