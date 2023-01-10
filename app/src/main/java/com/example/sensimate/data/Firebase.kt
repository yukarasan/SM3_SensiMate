package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.R
import com.example.sensimate.data.Database.fetchListOfEvents
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.ui.Event.createEvent.docId
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.HashMap


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

    suspend fun updateProfileFields(fields: Map<String, Any>) {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        withContext(Dispatchers.IO) {
            docRef.update(fields) // update
        }
    } // TODO: Yusuf

    fun updateEmail(currentPassword: String, newEmail: String, context: Context) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential =
            EmailAuthProvider.getCredential(auth.currentUser?.email.toString(), currentPassword)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.updateEmail(newEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Successfully updated your email",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d(TAG, "Error email not updated")
                    }
                }
            } else {
                Log.d(TAG, "Error auth failed")
                Toast.makeText(
                    context,
                    "Failed. Input does match e-mail or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    } // TODO: Yusuf

    fun deleteAndInsertEmailToFirestore(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        newEmail: String
    ) {
        Log.d("postal code", postalCode)
        Log.d("year born", yearBorn)
        Log.d("month born", monthBorn)
        Log.d("day born", dayBorn)
        Log.d("gender", gender)

        Log.d("Document: ", auth.currentUser?.email.toString())

        db.collection("users")
            .document(
                auth.currentUser?.email.toString()
            ).delete()

        setUpNewEmail(
            postalCode = postalCode,
            yearBorn = yearBorn,
            monthBorn = monthBorn,
            dayBorn = dayBorn,
            gender = gender,
            newEmail = newEmail
        )
    } // TODO: Yusuf

    fun updatePassword(currentPassword: String, newPassword: String, context: Context) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider
            .getCredential(auth.currentUser?.email.toString(), currentPassword)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener {
                    if (task.isSuccessful) {
                        Log.d(TAG, "Password updated")
                        Toast.makeText(
                            context, "Successfully updated your password",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d(TAG, "Error password not updated")
                    }
                }
            } else {
                Log.d(TAG, "Error auth failed")
                Toast.makeText(
                    context, "Failed. Input does not match current password",
                    Toast.LENGTH_SHORT
                ).show()
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
                        context,
                        context.resources.getString(R.string.successAcount),
                        Toast.LENGTH_SHORT
                    ).show()

                    setUpProfileInfo(postalCode, yearBorn, monthBorn, dayBorn, gender)

                } else {
                    showLoading.value = false
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.authFailed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun setUpNewEmail(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        newEmail: String
    ) {
        val profile = hashMapOf(
            "postalCode" to postalCode,
            "yearBorn" to yearBorn,
            "monthBorn" to monthBorn,
            "dayBorn" to dayBorn,
            "gender" to gender,
            "isEmployee" to false
        )

        db.collection("users").document(newEmail)
            .set(profile)
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
                        context,
                        context.resources.getString(R.string.successLogged),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    showLoading.value = false
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.loginFail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    } //TODO: Hussein

    fun loginAnonymously(context: Context) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.guestSuccessful),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.loginFail),
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
                    context,
                    context.resources.getString(R.string.recoveryMailSent),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.recoveryMailFail),
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
                    context,
                    context.resources.getString(R.string.deletedSuccess),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.somethingWrong),
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

        SaveBoolToLocalStorage(
            "isEmployee",
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

    fun createEvent(title: String,
                    description: String,
                    allergens: String,
                    location: String,
                    surveyCode: String,
                    //time: String,
                    day: String,
                    month: String,
                    year: String,
                    hour: String,
                    minute: String) {

        val event = hashMapOf(
            "title" to title,
            "description" to description,
            "allergens" to allergens,
            "location" to location,
            "surveyCode" to surveyCode,
            //"timeOfEvent" to time,
            "day" to day,
            "month" to month,
            "year" to year,
            "hour" to hour,
            "minute" to minute
        )
        db.collection("TESTER").add(event).addOnSuccessListener { docRef ->
            event.set("eventId", docRef.id)
            db.collection("TESTER").document(docRef.id).set(event)
            docId = docRef.id
        }
    } //TODO: Ahmad

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


    @SuppressLint("SuspiciousIndentation")
    fun UpdateEvent(event : HashMap<String, String>, documentID: String) {
        Log.d("eventId : ", documentID)

        val docref = db.collection("events").document(documentID)
            docref.set(event)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

    } //TODO: Sabirin


    suspend fun getSurveyAsList(eventId: String): List<MyQuestion> {
        val questions: MutableList<MyQuestion> = mutableListOf()
        val questionsRef = db.collection("events").document(eventId).collection("questions")

        val result = questionsRef.get().await()
        for (document in result) {
            // document contains a question data
            val newQuestion = MyQuestion()

            newQuestion.mainQuestion = document.getString("mainQuestion").toString()
            newQuestion.oneChoice = document.getBoolean("oneChoice") == true

            val optionResult = questionsRef.document(document.id)
                .collection("type")
                .document("options")
                .get().await()
            val myOptions = optionResult.data
            if (myOptions != null) {
                for (field in myOptions.keys) {
                    val value = myOptions[field]
                    newQuestion.options.add(value.toString())
                }
            }
            questions.add(newQuestion)
        }
        return questions
    }


    fun updateSurvey(eventId: String, survey: List<MyQuestion>) { //TODO: Ansh og (Hussein?)
        val questionsRef = db.collection("events").document(eventId).collection("questions")
        for (question in survey) {
            val docRef = questionsRef.document(question.mainQuestion)
            docRef.update("oneChoice", question.oneChoice)
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
            docRef.collection("type").document("options")
                .set(question.options.mapIndexed { index, i -> index.toString() to i }.toMap())
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
        }
    }


    /*

    suspend fun updateAnswer(eventId: String, questionId: String, answerId: String, newAnswer: Any) {
        val answerRef = db.collection("events").document(eventId).collection("questions")
            .document(questionId).collection("answers").document(answerId)
        answerRef.update("answer", newAnswer).await()
    }

     */

    /*
    suspend fun insertAnswer(eventId: String, questionId: String, answer: Any) {
        val answerRef = db.collection("events").document(eventId).collection("questions")
            .document(questionId).collection("answers").document()
        answerRef.set(mapOf("answer" to answer)).await()
    }

     */

    /*

    fun updateSurvey(eventId: String, questionId: String, newQuestion: MyQuestion) {
        val questionRef = db.collection("events").document(eventId)
            .collection("questions").document(questionId)

        questionRef.update("mainQuestion", newQuestion.mainQuestion)
        questionRef.update("oneChoice", newQuestion.oneChoice)

        questionRef.collection("type").get().addOnSuccessListener { options ->
            for (option in options) {
                questionRef.collection("type").document(option.id).delete()
            }
        }

        for (option in newQuestion.options) {
            questionRef.collection("type").add(option)
        }
    }

     */


    fun getEmployeeProfiles() {} //TODO: Sabirin

    fun createEmployee() {} //TODO: Anshjyot


    fun getSurveyWudia(eventId: String) {
        val db = FirebaseFirestore.getInstance()
        val eventsRef = db.collection("events")

        eventsRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val eventId = document.id
                    val eventRef = eventsRef.document(eventId)
                    val questionsRef = eventRef.collection("questions")
                    val questionRef = questionsRef.document("q1")

                    questionRef.get()
                        .addOnSuccessListener { result ->
                            val mainQuestion = result.getString("mainQuestion")
                            val oneChoice = result.getBoolean("oneChoice")

                            val optionsRef = questionRef.collection("options")
                            val answerRef = optionsRef.document("answer")

                            answerRef.get()
                                .addOnSuccessListener { result ->
                                    val answer = result.getString("answer")
                                    // val question = Question2(mainQuestion, oneChoice, answer)
                                }
                        }
                }
            }
    }

}

fun exportToExcel() {} //TODO: LATER


data class Question2(val mainQuestion: String, val oneChoice: Boolean, val answer: String)


object OurCalendar {
    fun getMonthName(month: Int): String? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    }
}
