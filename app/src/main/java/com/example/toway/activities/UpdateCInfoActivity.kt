package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.toway.R
import com.example.toway.databinding.ActivityCustomerProfileBinding
import com.example.toway.databinding.ActivityUpdateCinfoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateCInfoActivity : AppCompatActivity() {
    private val view: ActivityUpdateCinfoBinding by lazy { ActivityUpdateCinfoBinding.inflate(layoutInflater)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        val userId  = intent.getStringExtra("id")


        val etUsername = view.etUsername.text
        val etBirthday = view.etBirthday.text
        val etEmail = view.etEmail.text
        val etPhoneNumber = view.etPhoneNumber.text

        view.btnBack.setOnClickListener{
            startActivity(Intent(this@UpdateCInfoActivity, CustomerProfileActivity::class.java))
            finish()
        }

        view.btnUpdate.setOnClickListener {
            updateUser(userId.toString(), etUsername.toString(), etBirthday.toString(), etEmail.toString(), etPhoneNumber.toString())
        }
    }

    private fun updateUser(id: String, username: String, birthday: String, email: String, phoneNumber: String)
    {
        val updates = HashMap<String, Any>()
        updates["username"] = username
        updates["birthday"] = birthday
        updates["email"] = email
        updates["phoneNumber"] = phoneNumber
        updates["role"] = "Customer"

        databaseReference.orderByChild("id").equalTo(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val key = id // Get the user's key
                        databaseReference.child(key).updateChildren(updates)
                            .addOnSuccessListener {
                                Toast.makeText(this@UpdateCInfoActivity, "User updated successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@UpdateCInfoActivity, CustomerProfileActivity::class.java)
                                intent.putExtra("id", id)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { error ->
                                // Log the error for debugging
                                Log.e("CustomerProfileActivity", "Error updating user: $error")
                                Toast.makeText(this@UpdateCInfoActivity, "Error updating user", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this@UpdateCInfoActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Log the error for debugging
                    Log.e("CustomerProfileActivity", "Error fetching user data: $databaseError")
                    Toast.makeText(this@UpdateCInfoActivity, "Database error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}