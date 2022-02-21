package com.example.mindyourpet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mindyourpet2.R.id.login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth

        val loginButton: Button = findViewById(R.id.login)
        loginButton.setOnClickListener {
            val editTextEmail: EditText = findViewById(R.id.email)
            val email = editTextEmail.text.toString()
            val editTextPassword: EditText = findViewById(R.id.password)
            val password = editTextPassword.text.toString()

            signIn(email, password)
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

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("sign-in", "signInWithEmail:success")
                val intent = Intent(this, PetProfilesActivity::class.java)
                startActivity(intent)
            } else {
                Log.d("sign-in", "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, task.exception?.message ?:"Authentication Failed", Toast.LENGTH_SHORT).show()
            }

        }
    }
}