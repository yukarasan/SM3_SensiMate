package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import android.widget.Toast
import androidx.compose.runtime.*
import com.example.sensimate.data.Database.fetchListOfEvents
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore

data class EventScreenState(
    val events: MutableList<Event>? = null
)

class EventDataViewModel : ViewModel() {
    val state = mutableStateOf(EventScreenState())

    init {
        getListOfEvents()
    }

    private fun getListOfEvents() {
        viewModelScope.launch {
            val eventList = fetchListOfEvents()
            state.value = state.value.copy(events = eventList)
        }
    }
}

// Initialize Firebase Auth
val auth = Firebase.auth

object Database {
    //auth.currentUser?.email.toString()

    fun getProfile(): Profile {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        var profile = Profile()

        docRef.get().addOnSuccessListener { documentSnapshot ->
            profile = documentSnapshot.toObject<Profile>()!!
        }

        return profile;
    }


    fun signUserUp(
        email: String,
        password: String,
        context: Context,
        showLoading: MutableState<Boolean>,
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        successLoggedIn: MutableState<Boolean>
    ) {
        showLoading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showLoading.value = false
                    successLoggedIn.value = true
                    Toast.makeText(
                        context, "Account successfully created",
                        Toast.LENGTH_SHORT
                    ).show()

                    setUpProfileInfo(postalCode, yearBorn, monthBorn, dayBorn, gender)

                } else {
                    showLoading.value = false
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun setUpProfileInfo(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String
    ) {
        val profile = hashMapOf(
            "postalCode" to postalCode,
            "yearBorn" to yearBorn,
            "monthBorn" to monthBorn,
            "dayBorn" to dayBorn,
            "gender" to gender
        )

        db.collection("users").document(auth.currentUser?.email.toString())
            .set(profile)
    }

    fun logIn(
        email: String,
        password: String,
        showLoading: MutableState<Boolean>,
        context: Context,
        successLoggedIn: MutableState<Boolean>
    ) {
        showLoading.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showLoading.value = false
                    successLoggedIn.value = true
                    Toast.makeText(
                        context, "Account successfully logged in",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    showLoading.value = false
                    Toast.makeText(
                        context, "Log in failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    } //TODO: Hussein

    fun forgotPassword(
        email: String,
        context: Context,
        showLoading: MutableState<Boolean>,
    ) {
        showLoading.value = true
        auth.setLanguageCode("en") // Set to English
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->

            showLoading.value = false

            if (task.isSuccessful) {
                Toast.makeText(
                    context, "Successully sent a recovery e-mail",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context, "Failed to send e-mail. Is your e-mail correct?",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }//TODO: Hussein

    fun deleteProfile() {

    } //TODO: Hussein

    fun signOut() {
        auth.signOut()
    } //TODO: Hussein

    fun editUserProfile() {

    } //TODO: Yusuf

    suspend fun fetchListOfEvents(): MutableList<Event> {
        val eventReference = db.collection("events")
        val eventList: MutableList<Event> = mutableListOf()

        try {
            eventReference.get().await().map {
                val result = it.toObject(Event::class.java)
                eventList.add(result)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getListOfEvents: $e")
        }

        return eventList
    } //TODO: Yusuf


    /*
    item.forEach { document ->
                    val eventTitle = document.get("title").toString()
                    val eventAddress = document.get("address").toString()
                    val eventDescription = document.get("description").toString()
                    val eventDistance = document.get("distanceToEvent").toString()

                    val event = Event(
                        title = eventTitle,
                        address = eventAddress,
                        description = eventDescription,
                        distanceToEvent = eventDistance
                    )
                    events.add(event)
                }
     */

    fun createEvent() {} //TODO: Ahmad
    fun editEvent() {} //TODO: Ahmad


    fun deleteEvent(eventtitle: String) {
        //val docref = db.collection()
        db.collection("events").whereEqualTo("title", eventtitle)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener { Log.d(TAG, "Document has been deleted") }
                        .addOnFailureListener { e ->
                            Log.w(
                                TAG,
                                "Cant delete the current document", e
                            )
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    //TODO: Sabirin
    fun getEmployeeProfiles() {
    } //TODO: Sabirin

    fun createEmployee() {} //TODO: Anshjyot

    fun getSurvey() { //TODO: Anshjyot
        val questions = mutableListOf<Question>()
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("questions")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val questionSnapshots = snapshot.children
                for (questionSnapshot in questionSnapshots) {
                    val question = questionSnapshot.getValue(Question::class.java)
                    if (question != null) {
                        questions.add(question)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "getListOfQuestions: $error")
            }
        })
    }

    data class Question(val text: String, val answers: List<Boolean>)


    fun answerQuestion() {  //TODO: Anshjyot
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("answers")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val answers = snapshot.children.mapNotNull { it.getValue(Answer::class.java) }
                updateAnswers(answers)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "getListOfAnswers: $error")
            }
        })
    }

    fun updateAnswers(answers: List<Answer>) {

    }

    data class Answer(val questionId: String, val answer: List<Boolean>)


    fun exportToExcel() {} //TODO: LATER

}

object OurCalendar{
    fun getMonthName(month: Int): String? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    }
}