package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toway.R
import com.example.toway.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private val view: ActivityPaymentBinding by lazy { ActivityPaymentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnComfirmPayment.setOnClickListener {
            Toast.makeText(this@PaymentActivity, "Payment Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@PaymentActivity, CustomerHomePageActivity::class.java))
            finish()
        }
    }
}