package com.example.toway

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toway.databinding.ActivityRoleBinding

class RoleActivity : AppCompatActivity(){
    private val view: ActivityRoleBinding by lazy { ActivityRoleBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnCustomerRole.setOnClickListener{
            startActivity(Intent(this@RoleActivity, CustomerLoginActivity::class.java))
            finish()
        }
        view.btnDriverRole.setOnClickListener{
            startActivity(Intent(this@RoleActivity, DriverLoginActivity::class.java))
            finish()
        }
    }

}