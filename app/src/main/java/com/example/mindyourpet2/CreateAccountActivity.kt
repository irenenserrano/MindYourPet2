package com.example.mindyourpet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = Firebase.auth

        val createAccountButton: Button = findViewById(R.id.create_account)
        createAccountButton.setOnClickListener {
            val editTextEmail: EditText = findViewById(R.id.email)
            val email = editTextEmail.text.toString()
            val editTextPassword: EditText = findViewById(R.id.password)
            val password = editTextPassword.text.toString()

            createAccount(email, password)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, PetProfilesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                try {
                    Log.d("create-account", "createUserWithEmail:success")
                    val intent = Intent(this, MainPageActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Account Created, please login", Toast.LENGTH_LONG).show()
                } catch (existEmail: FirebaseAuthUserCollisionException) {
                    Log.d("create-account", "onComplete: exist_email");
                    Toast.makeText(this, "Account already exists", Toast.LENGTH_LONG).show()

                }
            } else {
                Log.w("create-account", "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}