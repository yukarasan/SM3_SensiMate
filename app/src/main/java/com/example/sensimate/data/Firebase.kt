package com.example.sensimate.data

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore


class Database(){

    fun login(){}
    fun editUserProfile(){}
    fun signUserUp(){}
    fun deleteProfile(){}
    fun createEmployee(){}

    fun createEvent(){}
    fun getEvents(){}
    fun getEvent(){}
    fun editEvent(){}
    fun deleteEvent(){}

    fun getEmployeeProfiles(){}
    fun getSurvey(){}

    fun answerQuestion(){}


}