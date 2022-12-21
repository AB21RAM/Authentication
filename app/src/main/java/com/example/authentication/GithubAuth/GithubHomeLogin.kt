package com.example.authentication.GithubAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.authentication.R
import com.example.authentication.databinding.ActivityGithubHomeLoginBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider

class GithubHomeLogin : AppCompatActivity(){
    lateinit var binding: ActivityGithubHomeLoginBinding
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var loginBtn: Button
    private lateinit var githubEdit: EditText
    private lateinit var auth: FirebaseAuth
    private val provider = OAuthProvider.newBuilder("github.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubHomeLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginBtn = binding.githubLoginBtn
        githubEdit = binding.githubId

        auth = FirebaseAuth.getInstance()
        provider.addCustomParameter("login", githubEdit.text.toString())
        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes
        loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(githubEdit.text.toString())) {
                Toast.makeText(this, "Enter your github id", Toast.LENGTH_LONG).show()
            } else {
                signInWithGithubProvider()
            }
        }
    }
    private fun signInWithGithubProvider() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    Toast.makeText(this, "User exist", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                }
        }
        else{

            auth.startActivityForSignInWithProvider( /* activity= */this, provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        firebaseUser = auth.currentUser!!
                        val intent = Intent(this, GithubCredentials::class.java)
                        this.startActivity(intent)
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                    })
        }
    }
}

