package com.example.authentication.GoogleAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authentication.HomeLoginScreen
import com.example.authentication.databinding.ActivityGoogleCredentialsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class GoogleCredentials : AppCompatActivity() {
    lateinit var binding : ActivityGoogleCredentialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
            binding.Name.setText(firebaseUser.displayName)
            binding.Email.setText(firebaseUser.email)
            Picasso.get().load(firebaseUser.photoUrl).resize(250,250).into(binding.Image)
        }
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this,HomeLoginScreen::class.java)
            startActivity(intent)
        }
    }
}