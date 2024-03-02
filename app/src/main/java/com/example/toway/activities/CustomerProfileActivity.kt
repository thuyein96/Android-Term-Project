package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*
import com.example.toway.R
import com.example.toway.databinding.ActivityCustomerProfileBinding
import com.example.toway.viewModels.Customer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerProfileActivity : AppCompatActivity() {
    private val view: ActivityCustomerProfileBinding by lazy { ActivityCustomerProfileBinding.inflate(layoutInflater)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    var customer: Customer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        val userId = intent.getStringExtra("id")
        getUserInfo(userId.toString())

        view.btnBack.setOnClickListener{
            startActivity(Intent(this@CustomerProfileActivity, CustomerHomePageActivity::class.java))
            finish()
        }

        view.btnEdit.setOnClickListener {
            val intent = Intent(this@CustomerProfileActivity, UpdateCInfoActivity::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
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
                            customer = userSnapshot.getValue(Customer::class.java)
                            view.tvUsername.text = customer?.username.toString()
                            view.tvBirthday.text = customer?.birthday.toString()
                            view.tvEmail.text = customer?.email.toString()
                            view.tvPhoneNumber.text = customer?.phoneNumber.toString()
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@CustomerProfileActivity,
                        "Database Error: ${databaseError.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }



//    private fun openUpdateDialog() {
//        val builder = Builder(this)
//        val inflater = LayoutInflater.from(this)
//        val view = inflater.inflate(R.layout.update_dialog, null)
//
//
//        builder.setView(view)
//
//        val etUsername = view.findViewById<TextView>(R.id.etUsername)
//        val etBirthday = view.findViewById<TextView>(R.id.etBirthday)
//        val etEmail = view.findViewById<TextView>(R.id.etEmail)
//        val etPhoneNumber = view.findViewById<TextView>(R.id.etPhoneNumber)
//        val btnUpdate = view.findViewById<TextView>(R.id.btnUpdate)
//
//        btnUpdate.setOnClickListener {
//            val username = etUsername.text.toString()
//            val birthday = etBirthday.text.toString()
//            val email = etEmail.text.toString()
//            val phoneNumber = etPhoneNumber.text.toString()
//
//            if (username.isNotEmpty() && birthday.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()) {
//                updateUser(username, birthday, email, phoneNumber)
//            } else {
//                Toast.makeText(this@CustomerProfileActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//    }
}