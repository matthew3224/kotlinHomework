package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        str = findViewById(R.id.edit_message)
        res = findViewById(R.id.textField)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val productApi = retrofit.create(ProductApi::class.java)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        res.text = viewModel.getData()

        findViewById<Button>(R.id.send).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(str.text.toString().toInt())
                runOnUiThread {
                    res.text = product.title
                    viewModel.setData(res.text.toString())
                }
            }
        }

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
