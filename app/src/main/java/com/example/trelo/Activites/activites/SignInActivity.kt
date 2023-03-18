package com.example.trelo.Activites.activites

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.trelo.Activites.firebase.FirestoreClass
import com.example.trelo.Activites.model.User
import com.example.trelo.R
import com.example.trelo.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : BaseActivity() {

    private var binding: ActivitySignInBinding?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth= Firebase.auth
        setupActionBar()


    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.signInToolbar)
        val actionBar= supportActionBar
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionBar.title=""
        }
        binding?.signInToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnSignIn?.setOnClickListener{
            signInRegisteredUser()
        }

    }

    fun signInSuccess(user: User){
        hideProgressDialog()
        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }

    private fun signInRegisteredUser(){
        val email: String=binding?.etEmailSignIn?.text.toString().trim{it <= ' '}
        val password: String=binding?.etPasswordSignIn?.text.toString().trim{it <= ' '}

        if (validateForm(email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        FirestoreClass().signInUser(this)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    private fun validateForm(email: String, password: String):Boolean{

        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter a email address")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            }else->{
                true
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}