package com.example.toway.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.toway.databinding.ActivityCustomerLoginBinding
import com.example.toway.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {
    private val view: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity,ActivityCustomerLoginBinding::class.java))
            finish()
        },2000)
    }
}