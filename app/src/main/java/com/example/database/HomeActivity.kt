package com.example.database

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    lateinit var dialog : Dialog
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val etName = findViewById<EditText>(R.id.editTextName)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPhone = findViewById<EditText>(R.id.editTextPhone)
        val addButton = findViewById<Button>(R.id.addBtn)

        val databaseUrl = "https://contacts-adfe2-default-rtdb.firebaseio.com/"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("Contacts")

        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialogue)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_alert_box))

        var buttonOk = dialog.findViewById<Button>(R.id.btnOk)

        buttonOk.setOnClickListener {
            dialog.dismiss()
        }

        addButton.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()


            val contact = Contact(name, email, phone)

            // Using the phone number directly as the unique child node key
            database.child(phone).setValue(contact)
                .addOnSuccessListener {
                    etName.text.clear()
                    etEmail.text.clear()
                    etPhone.text.clear()

                    dialog.show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show()
                }

        }
    }
}