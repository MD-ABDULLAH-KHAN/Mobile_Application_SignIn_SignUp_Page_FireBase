package com.example.database

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    companion object{
        const val KEY1 = "com.example.database.SignInActivity.mail"
        const val KEY2 = "com.example.database.SignInActivity.name"
        const val KEY3 = "com.example.database.SignInActivity.id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        val signInBtn = findViewById<Button>(R.id.btnSignIn)
        val userName = findViewById<TextInputEditText>(R.id.UserNameEditText)

        signInBtn.setOnClickListener {
            // take reference till node "Users"
            val uniqueId = userName.text.toString()
            if(uniqueId.isNotEmpty()){
                readData(uniqueId)
            }
            else{
                Toast.makeText(this,"Please enter Username", Toast.LENGTH_SHORT).show()
            }
        }
    } //onCreate Method over

    private fun readData(uniqueId: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(uniqueId).get().addOnSuccessListener {

            // If user exist or not
            if(it.exists()){
                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                val intentWelcome = Intent(this, HomeActivity::class.java)
                intentWelcome.putExtra(KEY1,email.toString())
                intentWelcome.putExtra(KEY2,name.toString())
                intentWelcome.putExtra(KEY3,userId.toString())
                startActivity(intentWelcome)
            }
            else {
                Toast.makeText(
                    this,
                    "User does not exist, Please first Sign Up",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Failed, Error in DB",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}