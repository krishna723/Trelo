package com.example.trelo.Activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.trelo.R
import com.example.trelo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

   // private var binding: ActivityMainBinding?=null

    private var toolbarMainActivity: Toolbar?= null
    private var drawerLayout: DrawerLayout?=null
    private var navView:NavigationView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        toolbarMainActivity=findViewById(R.id.toolbarMainActivity)
        drawerLayout=findViewById(R.id.drawerLayout)
        navView=findViewById(R.id.navView)
        setupActionBar()

        navView?.setNavigationItemSelectedListener(this)

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
                Toast.makeText(this@MainActivity,"My Profile",Toast.LENGTH_LONG).show()
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