package com.example.toway.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toway.adapters.OrderListAdapter
import com.example.toway.databinding.ActivityDriverHomepageBinding
import com.example.toway.viewModels.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DriverHomePageActivity: AppCompatActivity() {
    private val view: ActivityDriverHomepageBinding by lazy {
        ActivityDriverHomepageBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var callServices: ArrayList<Order>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        var userId = intent.getStringExtra("id")

        view.rvList.layoutManager = LinearLayoutManager(this)
        view.rvList.setHasFixedSize(true)

        callServices = arrayListOf<Order>()
        getOrders()

        view.btnProfile.setOnClickListener(
            {
                var intent = Intent(this@DriverHomePageActivity, DriverProfileActivity::class.java)
                intent.putExtra("id", userId)
                startActivity(intent)
                finish()
            }
        )
    }


    private fun getOrders() {

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Orders")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (childSnapshot in dataSnapshot.children) {
                        val dataObject = childSnapshot.getValue(Order::class.java)
                        callServices.add(dataObject!!)
                    }
                    view.rvList.adapter = OrderListAdapter(callServices)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Log.w("Firebase", "Error retrieving data: ${error.message}")
            }
        })
    }
}