package com.example.authentication.GithubAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.authentication.HomeLoginScreen
import com.example.authentication.MainActivity
import com.example.authentication.R
import com.example.authentication.databinding.ActivityGithubCredentialsBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class GithubCredentials : AppCompatActivity() {
    lateinit var binding: ActivityGithubCredentialsBinding
    var userName = ""

    private lateinit var githubUserName: TextView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logoutBtn = binding.logOut

        FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
            binding.id.setText(firebaseUser.displayName)
            Picasso.get().load(firebaseUser.photoUrl).resize(250,250).into(binding.imageView)
        }
        logoutBtn.setOnClickListener {
            val intent = Intent(this, HomeLoginScreen::class.java)
            this.startActivity(intent)
        }
    }
}