package com.example.toway

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toway.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CustomerHomePageActivity : AppCompatActivity() {
    private val view: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private lateinit var etFromLocation: EditText
    private lateinit var etToLocation: EditText
    private lateinit var btnGetDirection: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        etFromLocation = findViewById(R.id.etFromLocation)
        etToLocation = findViewById(R.id.etToLocation)
        btnGetDirection = findViewById(R.id.btnGetDirection)

        btnGetDirection.setOnClickListener{
            val userLocation = etFromLocation.getText().toString()
            val userDestination= etToLocation.getText().toString()

            if (userLocation.equals("") || userDestination.equals("")) {
                Toast.makeText(this, "Please enter your location & destination", Toast.LENGTH_SHORT).show()
            } else {
                getDirection(userLocation, userDestination)
            }
        }
    }

    private fun getDirection(from: String, to: String) {
        try {
            val uri = Uri.parse("https://www.google.com/maps/dir/" + from + "/" + to)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US&pli=1")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}