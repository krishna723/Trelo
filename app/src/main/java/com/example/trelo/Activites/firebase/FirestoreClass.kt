package com.example.trelo.Activites.firebase

import android.util.Log
import com.example.trelo.Activites.activites.SignInActivity
import com.example.trelo.Activites.activites.SignUpActivity
import com.example.trelo.Activites.model.User
import com.example.trelo.Activites.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore= FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User){

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistredSuccess()
            }.addOnFailureListener {
                e ->
                Log.e(activity.javaClass.simpleName,"Error occurs")
            }

    }

    fun signInUser(activity: SignInActivity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document->
                val loggedInUser=document.toObject(User::class.java)
                if (loggedInUser !=null){
                    activity.signInSuccess(loggedInUser)
                }


            }.addOnFailureListener {
                    e ->
                Log.e(activity.javaClass.simpleName,"Error occurs")
            }
    }

    fun getCurrentUserId(): String{

        var currentUser=FirebaseAuth.getInstance().currentUser
        var currentUserID=""
        if (currentUser !=null){
            currentUserID=currentUser.uid
        }
        return currentUserID

    }


}