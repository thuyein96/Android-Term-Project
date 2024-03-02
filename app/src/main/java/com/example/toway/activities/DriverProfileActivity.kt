package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toway.databinding.ActivityCustomerProfileBinding
import com.example.toway.viewModels.Customer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DriverProfileActivity : AppCompatActivity() {
    private val view: ActivityCustomerProfileBinding by lazy { ActivityCustomerProfileBinding.inflate(layoutInflater)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    var driver: Customer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        val userId = intent.getStringExtra("id")
        getUserInfo(userId.toString())

        view.btnBack.setOnClickListener{
            startActivity(Intent(this@DriverProfileActivity, DriverHomePageActivity::class.java))
            finish()
        }
    }

    private fun getUserInfo(userId: String) {
        databaseReference.orderByChild("id").equalTo(userId)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            driver = userSnapshot.getValue(Customer::class.java)
                            view.tvUsername.text = driver?.username.toString()
                            view.tvBirthday.text = driver?.birthday.toString()
                            view.tvEmail.text = driver?.email.toString()
                            view.tvPhoneNumber.text = driver?.phoneNumber.toString()
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@DriverProfileActivity,
                        "Database Error: ${databaseError.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}