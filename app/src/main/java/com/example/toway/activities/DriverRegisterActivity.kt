package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toway.databinding.ActivityDriverRegisterBinding
import com.example.toway.viewModels.Customer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DriverRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriverRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.signupButton.setOnClickListener{
            val registerUsername = binding.signupUsername.text.toString()
            val registerPassword = binding.signupPassword.text.toString()
            val registerBirthday = binding.signupBirthday.text.toString()
            val registerEmail = binding.signupEmail.text.toString()
            val registerPhoneNumber = binding.signupPhoneNumber.text.toString()
            val role = "Driver"

            if(registerUsername.isNotEmpty() && registerPassword.isNotEmpty()){
                signupUser(registerUsername, registerPassword, registerBirthday, registerEmail, registerPhoneNumber, role)
            } else {
                Toast.makeText(this@DriverRegisterActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginRedirect.setOnClickListener{
            startActivity(Intent(this@DriverRegisterActivity, DriverLoginActivity::class.java))
            finish()
        }
    }

    private fun signupUser(username: String, password: String, birthday: String, email: String, phoneNumber: String, role: String)
    {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){
                    val id = databaseReference.push().key
                    val customer = Customer(id, username, password, birthday, email, phoneNumber, role)
                    databaseReference.child(id!!).setValue(customer)
                    Toast.makeText(this@DriverRegisterActivity, "Signup Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DriverRegisterActivity, DriverLoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@DriverRegisterActivity, "User already exists", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DriverRegisterActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}