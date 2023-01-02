package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore


object Database{

    fun login(){} //TODO: Hussein
    fun signUserUp(){} //TODO: Hussein
    fun deleteProfile(){} //TODO: Hussein

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

    fun createEvent(){} //TODO: Ahmad
    fun editEvent(){} //TODO: Ahmad

    fun getOneEvent(){} //TODO: Sabirin
    fun deleteEvent(){} //TODO: Sabirin
    fun getEmployeeProfiles(){} //TODO: Sabirin

    fun createEmployee(){} //TODO: Anshjyot
    fun getSurvey(){} //TODO: Anshjyot
    fun answerQuestion(){} //TODO: Anshjyot

    fun exportToExcel(){} //TODO: LATER

}