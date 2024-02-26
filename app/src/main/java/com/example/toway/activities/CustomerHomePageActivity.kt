package com.example.toway.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.toway.databinding.ActivityMainBinding
import com.example.toway.utils.PermissionHandler
import com.example.toway.viewModels.Customer
import com.example.toway.viewModels.GPSViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.io.IOException

class CustomerHomePageActivity : AppCompatActivity() {
    private val view: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}
//    private val viewModel: GPSViewModel by viewModels()
//    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

//        PermissionHandler.requestPermissionIfRequired(this, PermissionHandler.GPS)
//        viewModel.getCurrentLocation()

//        view.mvGoogleMap.onCreate(savedInstanceState)
//
//        view.mvGoogleMap.getMapAsync {
//            map = it
//        }
//
//
//        viewModel.userLocation.observe(this@CustomerHomePageActivity) {
//            var currentLocation = LatLng(it.latitude, it.longitude)
//
//
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14f))
//
//            map.addMarker(MarkerOptions().position(currentLocation))
//        }

        view.btnCallService.setOnClickListener {
            startActivity(Intent(this@CustomerHomePageActivity, CallServiceActivity::class.java))
            finish()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        view.mvGoogleMap.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        view.mvGoogleMap.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        view.mvGoogleMap.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        view.mvGoogleMap.onStop()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        view.mvGoogleMap.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        view.mvGoogleMap.onLowMemory()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        view.mvGoogleMap.onSaveInstanceState(outState)
//    }
}