package com.example.toway.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.toway.databinding.ActivityCallServiceBinding
import com.example.toway.utils.PermissionHandler
import com.example.toway.viewModels.GPSViewModel
import com.example.toway.viewModels.Order
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class CallServiceActivity : AppCompatActivity() {
    private val view: ActivityCallServiceBinding by lazy {
        ActivityCallServiceBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: GPSViewModel by viewModels()
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var serviceCallRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)


//        val database = Firebase.database("https://toway-41887-default-rtdb.firebaseio.com/")
//        serviceCallRef = database.getReference("Orders")



        view.btnCallTow.setOnClickListener {
            var userName = view.etUserName.text.toString()
            var plateNumber = view.etPlateNumber.text.toString()
            var phone = view.etPhoneNumber.text.toString()
            var vehicleProblem = view.etVehicleProblem.text.toString()

            if (userName.isNotEmpty() && phone.isNotEmpty()) {
                recordOrder(
                    userName,
                    plateNumber,
                    phone,
                    vehicleProblem
                )
            } else {
                Toast.makeText(
                    this@CallServiceActivity,
                    "All fields are mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun recordOrder(userName: String, plateNumber: String, phone: String, vehicleProblem: String) {

        firebaseDatabase = FirebaseDatabase.getInstance()
        serviceCallRef = firebaseDatabase.reference.child("Orders")
        PermissionHandler.requestPermissionIfRequired(this, PermissionHandler.GPS)
        viewModel.getCurrentLocation()
        var location: LatLng? = null

        viewModel.userLocation.observe(this) {
            location = LatLng(it.latitude, it.longitude)
        }

        viewModel.error.observe(this@CallServiceActivity) {
            AlertDialog.Builder(this@CallServiceActivity)
                .setTitle("Something went wrong")
                .setMessage(it.message)
                .setPositiveButton("Ok") { dialog, _ -> dialog.cancel()}
                .show()
        }

        serviceCallRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val id = serviceCallRef.push().key
                    val order = Order(id, userName, location, plateNumber, phone, vehicleProblem, "")
                    serviceCallRef.child(id!!).setValue(order)
                    Toast.makeText(this@CallServiceActivity, "Calling Service Now", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@CallServiceActivity, ?????::class.java))
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CallServiceActivity, "Service Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }


}