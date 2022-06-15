package com.example.strippayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

val BackendUrl = "http://10.0.2.2:4242/"

class MainActivity : AppCompatActivity() {

    private val httpClient = OkHttpClient()
    private lateinit var publishableKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchPublishableKey()
    }

    private fun fetchPublishableKey(){
        val request = Request.Builder().url(BackendUrl + "config").build()

        httpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","request faild")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful){
                    Log.e("request failed","error +$response")
                }else {
                    val responseData = response.body?.string()
                    val responseJson = responseData?.let {
                        JSONObject(it)
                    } ?: JSONObject()
                    publishableKey = responseJson.getString("publishableKey")

                    PaymentConfiguration.init(applicationContext,publishableKey)
                }
            }
        })
    }
}