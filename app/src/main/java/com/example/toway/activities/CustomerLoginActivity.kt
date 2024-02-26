package com.example.toway.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toway.databinding.ActivityCustomerLoginBinding
import com.example.toway.viewModels.Customer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerLoginActivity : AppCompatActivity() {
    private val view: ActivityCustomerLoginBinding by lazy { ActivityCustomerLoginBinding.inflate(layoutInflater)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        view.loginButton.setOnClickListener{
            val loginUsername = view.loginUsername.text.toString()
            val loginPassword = view.loginPassword.text.toString()

            if(loginUsername.isNotEmpty() && loginPassword.isNotEmpty()){
                loginUser(loginUsername, loginPassword)
            } else {
                Toast.makeText(this@CustomerLoginActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        view.signupRedirect.setOnClickListener{
            startActivity(Intent(this@CustomerLoginActivity, CustomerRegisterActivity::class.java))
            finish()
        }
    }

    private fun loginUser(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val customer = userSnapshot.getValue(Customer::class.java)

                        if (customer != null && customer.password == password && customer.role == "Customer"){
                            Toast.makeText(this@CustomerLoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CustomerLoginActivity, CustomerHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@CustomerLoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@CustomerLoginActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}