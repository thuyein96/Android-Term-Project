package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.toway.R
import com.example.toway.databinding.ActivityOrderAcceptBinding
import com.example.toway.utils.PermissionHandler
import com.example.toway.viewModels.Customer
import com.example.toway.viewModels.GPSViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderAcceptActivity : AppCompatActivity() {
    private val view: ActivityOrderAcceptBinding by lazy { ActivityOrderAcceptBinding.inflate(layoutInflater)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private val viewModel: GPSViewModel by viewModels()
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Orders")

        PermissionHandler.requestPermissionIfRequired(this, PermissionHandler.GPS)
        viewModel.getCurrentLocation()

        view.mvRequestLocation.onCreate(savedInstanceState)

        view.mvRequestLocation.getMapAsync {
            map = it
        }

        val name = intent.getStringExtra("customername")
        val latitude  = intent.getDoubleExtra("latitute", 0.0)
        val longitude  = intent.getDoubleExtra("longitude", 0.0)
        val problem  = intent.getStringExtra("problem")
        val plateNumber  = intent.getStringExtra("plateNumber")
        val phoneNumber  = intent.getStringExtra("phoneNumber")

        view.tvUserName.setText(name)
        view.tvVehicleProblem.setText(problem)
        view.tvPlateNumber.setText(plateNumber)
        view.tvUserPh.setText(phoneNumber)

        viewModel.userLocation.observe(this@OrderAcceptActivity) {
            var currentLocation = LatLng(latitude, longitude)


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14f))

            map.addMarker(MarkerOptions().position(currentLocation))
        }

        view.btnCancel.setOnClickListener{
            startActivity(Intent(this@OrderAcceptActivity, DriverHomePageActivity::class.java))
            finish()
        }

        view.btnFinish.setOnClickListener{
            deleteRecord(name.toString())
            startActivity(Intent(this@OrderAcceptActivity, DriverCompleteActivity::class.java))
            finish()
        }
    }

    private fun deleteRecord(name: String){
        databaseReference.orderByChild("customerName").equalTo(name).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        userSnapshot.ref.removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OrderAcceptActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        view.mvRequestLocation.onStart()
    }

    override fun onResume() {
        super.onResume()
        view.mvRequestLocation.onResume()
    }

    override fun onPause() {
        super.onPause()
        view.mvRequestLocation.onPause()
    }

    override fun onStop() {
        super.onStop()
        view.mvRequestLocation.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        view.mvRequestLocation.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        view.mvRequestLocation.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        view.mvRequestLocation.onSaveInstanceState(outState)
    }
}