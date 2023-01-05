package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database.fetchListOfEvents
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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
} // TODO: Yusuf

// Initialize Firebase Auth
val auth = Firebase.auth

object Database {

    suspend fun fetchProfile(): Profile? {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        var profile: Profile?

        /*
        docRef.get()
            .addOnSuccessListener { snapshot ->
                profile = snapshot.toObject(Profile::class.java)
            }
         */

        withContext(Dispatchers.IO) {
            val snapshot = docRef.get().await()
            profile = snapshot.toObject(Profile::class.java)
        }

        return profile
    } // TODO: Yusuf


    suspend fun fetchListOfEvents(): MutableList<Event> {
        val eventReference = db.collection("events")
        val eventList: MutableList<Event> = mutableListOf()

        try {
            eventReference.get().await().map {
                val result = it.toObject(Event::class.java)
                eventList.add(result)
                it.id
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getListOfEvents: $e")
        }

        return eventList
    } // TODO: Yusuf


    suspend fun updateProfile(fields: Map<String, Any>) {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        withContext(Dispatchers.IO) {
            docRef.update(fields)
        }
    } // TODO: Yusuf

    fun updatePassword(currentPassword: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider
            .getCredential(auth.currentUser?.email.toString(), currentPassword)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener {
                    if (task.isSuccessful) {
                        Log.d(TAG, "Password updated")
                    } else {
                        Log.d(TAG, "Error password not updated")
                    }
                }
            } else {
                Log.d(TAG, "Error auth failed")
            }
        }
    } // TODO: Yusuf

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
            "gender" to gender,
            "isEmployee" to false
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

    fun loginAnonymously(context: Context) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context, "Successfully logged in anonymously",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context, "Log in failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        /*
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInAnonymously:success")
            val user = auth.currentUser
            updateUI(user)
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInAnonymously:failure", task.exception)
            Toast.makeText(
                baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
            updateUI(null)
        }
    }

         */
    }

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

    fun deleteProfile(context: Context) {


        db.collection("users")
            .document(
                auth.currentUser?.email.toString()
            ).delete()

        auth.currentUser?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context, "Account successfully deleted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context, "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    } //TODO: Hussein

    fun signOut(context: Context) {
        auth.signOut()
        SaveBoolToLocalStorage(
            "isLoggedIn",
            false,
            context = context
        )

        SaveBoolToLocalStorage(
            "isGuest",
            false,
            context
        )

    } //TODO: Hussein

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

    fun UpdateEvent(data: Map<String, String>, documentID: String) {
        val docref = db.collection("events").document(documentID)
        docref.update(data)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

    } //TODO: Sabirin


    fun getEmployeeProfiles() {} //TODO: Sabirin

    fun createEmployee() {} //TODO: Anshjyot


    suspend fun getSurveyQuestions(): List<SurveyQuestion> {
        val surveyQuestions = mutableListOf<SurveyQuestion>()
        val querySnapshot = db.collection("Survey").get().await()
        for (document in querySnapshot.documents) {
            val data = document.data
            val question = data?.get("Questions") as List<String>
            val answers = data["answers"] as List<Boolean>
            val surveyQuestion = SurveyQuestion(question, answers)
            surveyQuestions.add(surveyQuestion)
        }
        return surveyQuestions
    }

    data class SurveyQuestion(val text: List<String>, val answers: List<Boolean>)


    suspend fun updateSurveyAnswers(questionId: String, answers: List<Boolean>) {
        val db = FirebaseFirestore.getInstance()
        val surveyRef = db.collection("Survey").document(questionId)
        surveyRef.update("answers", answers)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully updated!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Updated failed", exception)
            }
    }


/*
    fun getSurvey(): MutableList<SurveyQuestions> {
        val db = Firebase.firestore
        val surveyCollection = db.collection("events")
        val surveyQuestions = mutableListOf<SurveyQuestions>()

        surveyCollection.get().addOnSuccessListener { snapshot ->
            for (document in snapshot) {
                val main = document.getString("main") ?: ""
                val sub = document.get("sub") as List<String>
                val type = document.getString("type") ?: ""
                val question = SurveyQuestions(main, sub, type)
                surveyQuestions.add(question)
            }
        }.addOnFailureListener { exception ->
            Log.w("SurveyScreen", "Error.", exception)
        }

        return surveyQuestions
    }

 */

/*
    fun getSurvey(eventId: String) {
        val eventsRef = FirebaseFirestore.getInstance().collection("events").document(eventId)
        val questionsRef = eventsRef.collection("questions")

        questionsRef.get().addOnSuccessListener { snapshot ->
            val questions = snapshot.documents.map { document ->
                val main = document.getString("main")
                val sub = document.get("sub") as List<String>
                val type = document.getString("type")

                if (type != null) {
                    if (main != null) {
                        Question(main, sub, type)
                    }
                }

            }
            // Now you have a list of `Question` objects that you can use to build your survey UI
        }
    }

 */
/*

    fun getSurvey(eventId: String) {
        val eventsRef = FirebaseFirestore.getInstance().collection("events").document(eventId)
        val questionsRef = eventsRef.collection("questions")

        questionsRef.get().addOnSuccessListener { snapshot ->
            val questions = snapshot.documents.map { document ->
                val main = document.getString("main")
                val sub = document.get("sub") as List<String>
                val type = document.getString("type")

                if (type != null && main != null) {
                    Question(main, sub, type)
                }
            }

            // Now you have a list of `Question` objects that you can use to build your survey UI
            // using Jetpack Compose
            Survey(questions)
        }
    }

 */



    data class Question(val main: String, val sub: List<String>, val type: String)




    fun exportToExcel() {} //TODO: LATER


    object OurCalendar {
        fun getMonthName(month: Int): String? {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, month)
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        }


        fun getSurveyAsList(eventId: String) {
            val questionsRef = db.collection("events").document(eventId).collection("questions")
            questionsRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // document contains a question data
                    }
                }
                .addOnFailureListener { exception ->
                    // handle failure
                }
        }

    }

}