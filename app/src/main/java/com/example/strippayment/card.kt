package com.example.strippayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe

class card : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        stripe = Stripe(this, PaymentConfiguration.getInstance(applicationContext).publishableKey)
    }

    private fun startCheckout() {
        ApiClient().createPaymentIntent("card", "usd", completion = {
            paymentIntentClientSecret, error ->
            run {
                paymentIntentClientSecret?.let {
                    this.paymentIntentClientSecret = it
                }
            }
        })
    }
}