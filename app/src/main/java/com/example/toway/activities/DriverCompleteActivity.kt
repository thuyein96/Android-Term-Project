package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toway.R
import com.example.toway.databinding.ActivityDriverCompleteBinding

class DriverCompleteActivity : AppCompatActivity() {
    private val view: ActivityDriverCompleteBinding by lazy { ActivityDriverCompleteBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnFinishD.setOnClickListener{
            startActivity(Intent(this@DriverCompleteActivity, DriverHomePageActivity::class.java))
            finish()
        }


    }
}