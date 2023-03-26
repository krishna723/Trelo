package com.example.trelo.Activites.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.trelo.Activites.firebase.FirestoreClass
import com.example.trelo.Activites.model.User
import com.example.trelo.R
import com.example.trelo.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private var binding:ActivityProfileBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()

        FirestoreClass().loadUserData(this)
    }


    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarMyProfileActivity)
        val actionBar=supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionBar.title=resources.getString(R.string.my_profile)
        }

        binding?.toolbarMyProfileActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun setUserData(user: User){
        binding?.ivUserImage?.let {
            Glide
                .with(this@ProfileActivity)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(it)
        }
        binding?.etName?.setText(user.name)
        binding?.etEmail?.setText(user.email)

        if (user.mobile !=0L){
            binding?.etMobile?.setText(user.mobile.toString())
        }

    }
}