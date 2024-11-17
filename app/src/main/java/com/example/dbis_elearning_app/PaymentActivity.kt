package com.example.dbis_elearning_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : ComponentActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)
        initPayment()
    }

    private fun initPayment() {
        val co = Checkout()
        co.setKeyID("rzp_test_L3zcJSEgi9lvEQ")  // Replace with your actual key

        try {
            val options = JSONObject()
            options.put("name", "Amber")
            options.put("description", "E-Learning Course")
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            //options.put("order_id", "order_DBJOWzybf0sJbb") // Replace with a valid order_id
            options.put("amount", "50000")  // Amount in currency subunits (e.g., 50000 = ₹500)

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", "yashkumbkarn85@gmail.com")
            prefill.put("contact", "9373182023")
            options.put("prefill", prefill)

            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show()
        Log.d("PaymentSuccess", "Payment successful with id: $p0 and data: $p1")
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment failed: $p1", Toast.LENGTH_LONG).show()
        Log.d("PaymentError", "Payment failed with error $p1 and code $p0")
        // Implement any additional error handling
    }
}
