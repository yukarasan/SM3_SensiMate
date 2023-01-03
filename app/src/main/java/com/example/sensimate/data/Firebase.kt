package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore

// Initialize Firebase Auth
val auth = Firebase.auth

object Database {

    fun signUserUp(
        email: String,
        password: String,
        context: Context,
        showLoading: MutableState<Boolean>
    ) {
        showLoading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showLoading.value = false
                    Toast.makeText(
                        context, "Account successfully created",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading.value = false
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    fun logIn() {} //TODO: Hussein
    fun deleteProfile() {} //TODO: Hussein

    fun signOut() {

    } //TODO: Yusuf

    fun editUserProfile() {

    } //TODO: Yusuf

    fun getListOfEvents(): List<Event> {
        val events = mutableListOf<Event>()
        val eventReference = db.collection("events")

        eventReference.get()
            .addOnSuccessListener { item ->
                item.forEach { document ->
                    val event = document.toObject(Event::class.java)
                    events.add(event)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        return events
    } //TODO: Yusuf

    fun createEvent() {} //TODO: Ahmad
    fun editEvent() {} //TODO: Ahmad

    fun getOneEvent() {} //TODO: Sabirin
    fun deleteEvent() {} //TODO: Sabirin
    fun getEmployeeProfiles() {} //TODO: Sabirin

    fun createEmployee() {} //TODO: Anshjyot
    fun getSurvey() {} //TODO: Anshjyot
    fun answerQuestion() {} //TODO: Anshjyot

    fun exportToExcel() {} //TODO: LATER

}