package com.example.trelo.Activites.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.trelo.Activites.firebase.FirestoreClass
import com.example.trelo.Activites.model.User
import com.example.trelo.R
import com.example.trelo.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    private var binding: ActivitySignUpBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBar()

    }

    fun userRegistredSuccess(){
        Toast.makeText(
            this,
            "You have registered Successfully",
            Toast.LENGTH_LONG
        ).show()

        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.signUpToolbar)
        val actionBar= supportActionBar
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionBar.title=""
        }
        binding?.signUpToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnSignUp?.setOnClickListener{
            registerUser()
        }
    }
    private fun registerUser(){
        val name: String=binding?.etName?.text.toString().trim{it <= ' '}
        val email: String=binding?.etEmail?.text.toString().trim{it <= ' '}
        val password: String=binding?.etPassword?.text.toString().trim{it <= ' '}
        if(validateForm(name,email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user =User(firebaseUser.uid,name,registeredEmail)
                    FirestoreClass().registerUser(this,user)
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message, Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun validateForm(name: String,email: String, password: String):Boolean{



        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter a email address")
                false
            }TextUtils.isEmpty(password)->{
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