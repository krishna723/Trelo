package com.example.trelo.Activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.trelo.Activites.firebase.FirestoreClass
import com.example.trelo.Activites.model.User
import com.example.trelo.R
import com.example.trelo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

   // private var binding: ActivityMainBinding?=null

    private var toolbarMainActivity: Toolbar?= null
    private var drawerLayout: DrawerLayout?=null
    private var navView:NavigationView?=null
    private var navUserImage: CircleImageView?=null
    private var tvUserName : TextView?=null
    private var tvMain: TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        toolbarMainActivity=findViewById(R.id.toolbarMainActivity)
        drawerLayout=findViewById(R.id.drawerLayout)
        navView=findViewById(R.id.navView)
        navUserImage=findViewById(R.id.iv_user_image)

        tvUserName=findViewById(R.id.tvUserName)
        tvMain=findViewById(R.id.tvMain)
        setupActionBar()

        navView?.setNavigationItemSelectedListener(this)
        FirestoreClass().loadUserData(this)

    }


    fun updateNavigationUserDetails(user: User){

//        Log.d("User",user.name.toString())

        navUserImage?.let {
            Glide
                .with(this)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(it)
        }
        tvUserName?.text = user.name


    }
    private fun setupActionBar(){
        setSupportActionBar(toolbarMainActivity)
        toolbarMainActivity!!.setNavigationIcon(R.drawable.ic_action_nevication_menu)

        toolbarMainActivity!!.setNavigationOnClickListener {

            toggleDrawer()

        }
    }

    private fun toggleDrawer(){
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            drawerLayout!!.closeDrawer(GravityCompat.START)
        }else{
            drawerLayout!!.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {

        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            drawerLayout!!.closeDrawer(GravityCompat.START)
        }else{
            doubleBackToExit()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.navMyProfile->{
                startActivity(Intent(this,ProfileActivity::class.java))
            }
            R.id.navSignOut->{
                FirebaseAuth.getInstance().signOut()

                val intent=Intent(this,IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)
                finish()

            }

        }

        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

}