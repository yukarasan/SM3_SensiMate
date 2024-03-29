package com.example.sensimate.data

//import com.example.sensimate.data.questionandsurvey.MyAnswer
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import com.example.sensimate.R
import com.example.sensimate.data.QuestionandSurvey.MyQuestion
import com.example.sensimate.ui.Event.viewModels.Event
import com.example.sensimate.ui.createEvent.docId
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap


@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore

// Initialize Firebase Auth
val auth = Firebase.auth

object Database {

    /**
     * This function is used to fetch the user's profile information from the Firebase
     * FireStore database.
     * It retrieves the document of the current user from the "users" collection and converts
     * it to a Profile object.
     * @return The Profile object of the current user or null if no such document exists.
     * @author Yusuf Kara
     */
    suspend fun fetchProfile(): Profile? {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        var profile: Profile?

        withContext(Dispatchers.IO) {
            val snapshot = docRef.get().await()
            profile = snapshot.toObject(Profile::class.java)
        }

        return profile
    }

    /**
     * This function is used to fetch a list of events from the Firebase Firestore database.
     * It retrieves all documents from the "events" collection and converts them to a list of
     * Event objects.
     * That way the events can be represented in a lazyColumn in the Event screens.
     * @return A MutableList of Event objects representing the events in the database.
     * @author Yusuf Kara
     */
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
    }

    /**
     * This function is used to update fields in the user's profile document in the Firebase
     * FireStore database.
     * It retrieves the document of the current user from the "users" collection and updates
     * the provided fields.
     * @param fields A map of field names and their corresponding values to update in the document.
     * @author Yusuf Kara
     */
    suspend fun updateProfileFields(fields: Map<String, Any>) {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        withContext(Dispatchers.IO) {
            docRef.update(fields) // update
        }
    }

    /**
     * This function updates the email of the authenticated user.
     * It first re-authenticates the user using the provided current password and their
     * current email.
     * It then updates the email in Firebase's Authentication service and in the Firestore database
     * itself.
     * @author Yusuf Kara
     */
    fun updateEmail(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        currentPassword: String,
        newEmail: String,
        context: Context
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential =
            EmailAuthProvider.getCredential(auth.currentUser?.email.toString(), currentPassword)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                deleteAndInsertEmailToFireStore(
                    postalCode = postalCode,
                    yearBorn = yearBorn,
                    monthBorn = monthBorn,
                    dayBorn = dayBorn,
                    gender = gender,
                    newEmail = newEmail
                )

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
    }

    /**
     * This function deletes the current user's profile from FireStore's users collection
     * and reinserts it with the new email.
     * @author Yusuf Kara
     */
    fun deleteAndInsertEmailToFireStore(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        newEmail: String
    ) {
        // Log.d("Document: ", auth.currentUser?.email.toString())

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
            newEmail = newEmail,
            isEmployee = false
        )
    }

    /**
     * Update the password of the currently logged in user using Firebase Authentication.
     * It first checks if the user trying to update their password is actually them, by first
     * asking for their own password, so they can get authenticated.
     * While this method may be effective, it should be noted that it lacks a certain level
     * of security. Specifically, the current password can be easily obtained by printing it out.
     * To ensure a more secure solution, incorporating encryption would be recommended.
     * However, as it is not a requirement for the current course of study, the current method
     * remains sufficient for the time being.
     * @param currentPassword the current password of the user
     * @param newPassword the new password to be set
     * @param context the context of the activity or fragment that calls this method
     * @author Yusuf Kara
     */
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
    }

    /**
     * Deletes the current anonymous user from Firebase Authentication.
     * @author Yusuf Kara
     */
    fun deleteGuestUser(context: Context) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null && currentUser.isAnonymous) {
            currentUser.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        SaveBoolToLocalStorage("isGuest", false, context)
                        Toast.makeText(
                            context, "Anonymous user deleted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context, "Failed to delete anonymous user.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                context, "User is not anonymous.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * This function is used to log the user in with their email and password. It also updates the
     * 'showLoading' state variable to indicate that the login process is currently in progress.
     * If the login is successful, the 'successLoggedIn' state variable is updated to indicate that the user
     * is now logged in.
     * @author hussein el-zein
     */
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
        successLoggedIn: MutableState<Boolean>,
        isEmployee: Boolean
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

                    setUpProfileInfo(
                        postalCode,
                        yearBorn,
                        monthBorn,
                        dayBorn,
                        gender,
                        isEmployee
                    )

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
        newEmail: String,
        isEmployee: Boolean
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

    /**
     * This function sets up user's profile information and stores it in the firestore database
     * @author hussein el-zein
     * @param isEmployee whether the user is an employee or not
     */
    fun setUpProfileInfo(
        postalCode: String,
        yearBorn: String,
        monthBorn: String,
        dayBorn: String,
        gender: String,
        isEmployee: Boolean
    ) {
        val profile = hashMapOf(
            "postalCode" to postalCode,
            "yearBorn" to yearBorn,
            "monthBorn" to monthBorn,
            "dayBorn" to dayBorn,
            "gender" to gender,
            "isEmployee" to isEmployee,
            "isAdmin" to false
        )

        db.collection("users").document(auth.currentUser?.email.toString())
            .set(profile)
    }


    /**
     * logIn function is used to log in the user into the application
     * by taking the email and password as arguments and authenticating them against
     * the firebase authentication service.
     * @param email The email of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @param showLoading MutableState object to control the visibility of the loading indicator.
     * @param context The context of the activity or fragment calling the function.
     * @param successLoggedIn MutableState object to store the success of the login operation.
     * @author hussein el-zein
     */
    fun logIn(
        email: String,
        password: String,
        showLoading: MutableState<Boolean>,
        context: Context,
        successLoggedIn: MutableState<Boolean>,
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

    /**
     * retrieving if the active user is an employee
     *
     * @author hussein el-zein
     */
    suspend fun getIsEmployee(context: Context): Boolean {

        val isEmp = mutableStateOf(false)

        val myDb = db.collection("users").document(auth.currentUser?.email.toString())

        myDb.get().addOnSuccessListener { documentSnapshot ->
            val myField = documentSnapshot.getBoolean("isEmployee") == true

            if (myField == true) {
                SaveBoolToLocalStorage("isEmployee", true, context)
                isEmp.value = true
            }
        }.await()

        return isEmp.value

    }//TODO: Hussein

    /**
     * retrieving if the current user is an admin
     *
     * @author hussein el-zein
     */
    suspend fun getIsAdmin(context: Context): Boolean {

        val isEmp = mutableStateOf(false)

        val myDb = db.collection("users").document(auth.currentUser?.email.toString())

        myDb.get().addOnSuccessListener { documentSnapshot ->
            val myField = documentSnapshot.getBoolean("isAdmin") == true

            if (myField == true) {
                SaveBoolToLocalStorage("isEmployee", true, context)
                SaveBoolToLocalStorage("isAdmin", true, context)
                isEmp.value = true
            }
        }.await()

        return isEmp.value

    }//TODO: Hussein

    /**
     * when logging in as a "guest", firebase knows it as anonymous
     *
     * @author hussein el-zein
     */
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

    /**
     * getAllEmployeesList is a suspending function that retrieves all the emails of the employees from firestore.
     * @author Hussein El-Zein
     */
    suspend fun getAllEmployeesList(): MutableList<String> {
        val usersMail = mutableListOf<String>()
        val query = FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("isEmployee", true)

        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    usersMail.add(document.id)
                }
            }.await()
        return usersMail
    }//TODO: Hussein

    /**
     * update the employee status to unemployed
     *
     * @author hussein el-zein
     */
    fun unemployProfile(context: Context, email: String) {
        db.collection("users")
            .document(
                email
            ).update("isEmployee", false)
    } //TODO: Hussein

    /**
     * update the employee status to employed
     * @author hussein el-zein
     */
    fun employProfile(context: Context, email: String) {
        db.collection("users")
            .document(
                email
            ).update("isEmployee", true).addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Employee is created",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener() {
                Toast.makeText(
                    context,
                    "failed, and employee couldnt be created",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } //TODO: Hussein

    /**
     * send recovery mail to user
     * @author hussein el-zein
     */
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


    /**
     * delete the user account and profile
     * @author hussein el-zein
     */
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

        SaveBoolToLocalStorage(
            "isAdmin",
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
    /**
     * @author Ahmad Sandhu
     * This function sends the event date when creating an event to firebase database
     */

    fun createEvent(
        title: String,
        description: String,
        allergens: String,
        location: String,
        surveyCode: String,
        day: String,
        month: String,
        year: String,
        hour: String,
        minute: String,
        //eventCode : String
    ) {
        val event = hashMapOf(
            "title" to title,
            "description" to description,
            "allergens" to allergens,
            "location" to location,
            "surveyCode" to surveyCode,
            "day" to day,
            "month" to month,
            "year" to year,
            "hour" to hour,
            "minute" to minute,
            //"eventCode" to eventCode
        )
        db.collection("events").add(event).addOnSuccessListener { docRef ->
            event.set("eventId", docRef.id)
            db.collection("events").document(docRef.id).set(event)
            docId = docRef.id
        }
    } //TODO: Ahmad

    /**
     * @author Ahmad Sandhu
     * This function sends the question data when creating an question to firebase database
     */
    fun question5(
        question: String,
        boolean: Boolean,
        answer1: String,
        answer2: String,
        answer3: String,
        answer4: String,
        answer5: String
    ) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question,
            "oneChoice" to boolean
        )
        val questionAnswer = hashMapOf(
            "answer1" to answer1,
            "answer2" to answer2,
            "answer3" to answer3,
            "answer4" to answer4,
            "answer5" to answer5
        )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
            mainQuest.set("questionId", docRef.id)
            subcollectionRef.document(docRef.id).collection("type").document("options")
                .set(questionAnswer)
        }
    }//TODO: Ahmad

    /**
     * @author Ahmad Sandhu
     * This function sends the question data when creating an question to firebase database
     */
    fun question4(
        question: String,
        boolean: Boolean,
        answer1: String,
        answer2: String,
        answer3: String,
        answer4: String
    ) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question,
            "oneChoice" to boolean
        )
        val questionAnswer = hashMapOf(
            "answer1" to answer1,
            "answer2" to answer2,
            "answer3" to answer3,
            "answer4" to answer4,
        )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
            mainQuest.set("questionId", docRef.id)
            subcollectionRef.document(docRef.id).collection("type").document("options")
                .set(questionAnswer)
        }
    }//TODO: Ahmad

    /**
     * @author Ahmad Sandhu
     * This function sends the question data when creating an question to firebase database
     */
    fun question3(
        question: String,
        boolean: Boolean,
        answer1: String,
        answer2: String,
        answer3: String
    ) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question,
            "oneChoice" to boolean
        )
        val questionAnswer = hashMapOf(
            "answer1" to answer1,
            "answer2" to answer2,
            "answer3" to answer3,

            )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
            mainQuest.set("questionId", docRef.id)
            subcollectionRef.document(docRef.id).collection("type").document("options")
                .set(questionAnswer)
        }
    }//TODO: Ahmad

    /**
     * @author Ahmad Sandhu
     * This function sends the question data when creating an question to firebase database
     */
    fun question(question: String, boolean: Boolean, answer1: String, answer2: String) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question,
            "oneChoice" to boolean
        )
        val questionAnswer = hashMapOf(
            "answer1" to answer1,
            "answer2" to answer2,

            )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
            mainQuest.set("questionId", docRef.id)
            subcollectionRef.document(docRef.id).collection("type").document("options")
                .set(questionAnswer)
        }
    }//TODO: Ahmad

    /**
     * @author Ahmad Sandhu
     * This function sends the question data when creating an question to firebase database
     */
    fun textAnswer(question: String) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question
        )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest)
    }//TODO: Ahmad

    /**
     * This function deleteEvent, is created to delete the event, once this function is invocted in
     * firebase. It takes a eventTitle as an argument, so based of the eventTitle it should delete
     * the event. If the event is deleted we would get an alert message  and also if the event couldn't be
     * deleted we would also get an alert message
     * @author Sabirin Omar.
     */

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

    /**
     * This function is used to update an Event as an employee user, so once this function is used
     * it would eventually update the event based of the the giving event documentID.
     * @author Sabirin Omar
     */

    @SuppressLint("SuspiciousIndentation")
    fun UpdateEvent(event: HashMap<String, String>, documentID: String) {
        Log.d("eventId : ", documentID)

        val docref = db.collection("events").document(documentID)
        docref.set(event)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

    }


    /**
     * Retrieves a list of all the questions associated with an event
     * @author Hussein El-Zein
     * @param eventId the id of the event to retrieve questions from
     * @return list of all questions associated with the event
     */
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

    suspend fun insertAnswer(eventId: String, questionId: String, answer: Any) {
        val answerRef = db.collection("events").document(eventId).collection("questions")
            .document(questionId).collection("answers").document()
        answerRef.set(mapOf("answer" to answer)).await()
    }


    /**
     * @author Anshjyot Singh
     * updateSurvey is a suspend function used to update the survey with the current answers in the database.
     * @param context : the context of the application
     * @param eventId : the id of the event the survey belongs to
     * @param options : list of answers for the current question
     * @param newQuestion : the current question
     */
    suspend fun updateSurvey(
        context: Context,
        eventId: String,
        options: List<String>,
        newQuestion: MyQuestion,
        boolean: Boolean
    ) {
        val test = hashMapOf(
            "mainQuestion" to newQuestion.mainQuestion
        )


        val profile = fetchProfile()!!


        val survey = hashMapOf(
            "postalCode" to profile.postalCode,
            "yearBorn" to profile.yearBorn,
            "monthBorn" to profile.monthBorn,
            "dayBorn" to profile.dayBorn,
            "gender" to profile.gender,
            "answer" to options.toString(),
            "isEmployee" to false
        )
        val scope = MainScope()

        val questionRef = db.collection("events").document(eventId)
            .collection("Answers").add(test).addOnSuccessListener { docRef ->
                docRef.collection("users").add(survey).addOnSuccessListener { docRef ->

                    if (boolean) {
                        scope.launch {
                            main(context = context, newQuestion, options)
                        }

                    }

                }
            }
    }


    /** Code inspiration regarding Excel implementation, function: main, getHeaderStyle,
     * createSheetHeader, addData, CreateCell, CreateExcel from
     * https://medium.com/geekculture/a-simple-way-to-work-with-excel-in-android-app-94c727e9a138 *
     */

    /**
     * @author Anshjyot Singh
     * addAnswer is a function that adds a new answer to the list of current answers and also adds it to a map with the current question as the key.
     * updateAnswer is a function that updates the survey in the database by passing the eventId, boolean, and context as arguments.
     * updateSurvey is a suspend function used to update the survey with the current answers in the database.
     * It takes context, eventId, options, newQuestion, and boolean as arguments.
     * main is a suspend function used to create and update an excel file with the survey results.
     * It creates a new workbook, creates a new sheet, sets the header style, adds data to the sheet, and creates the excel file.
     * It takes context, newQuestion, and options as arguments.
     * getHeaderStyle is a private function used to set the header style for the excel sheet.
     * createSheetHeader is a private function used to create the header for the excel sheet.
     */


    @SuppressLint("SuspiciousIndentation")
    suspend fun main(context: Context, newQuestion: MyQuestion, options: List<String>) {

        val workbook = XSSFWorkbook()

        val sheet: Sheet = workbook.createSheet("Survey Results")


        val cellStyle = getHeaderStyle(workbook)


        createSheetHeader(cellStyle, sheet)

        val profile = fetchProfile()!!

        addData(0, sheet, newQuestion = newQuestion, options, profile)


        createExcel(workbook, context)

    }


}

/*
private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
//setHeaderStyle is a custom function written below to add header style
//Create sheet first row
val row = sheet.createRow(0)
//Header list
val HEADER_LIST = listOf(
    "mainQuestion",
    "postalCode",
    "yearBorn",
    "monthBorn",
    "dayBorn",
    "gender",
    "answer",
    "isEmployee"
)
//Loop to populate each column of header row
for ((index, value) in HEADER_LIST.withIndex()) {
    val columnWidth = (15 * 500)
    //index represents the column number
    sheet.setColumnWidth(index, columnWidth)
    //Create cell
    val cell = row.createCell(index)
    //value represents the header value from HEADER_LIST
    cell?.setCellValue(value)
    //Apply style to cell
    cell.cellStyle = cellStyle
}
}
*/

private fun getHeaderStyle(workbook: Workbook): CellStyle {

    //Cell style for header row
    val cellStyle: CellStyle = workbook.createCellStyle()

    //Apply cell color
    val colorMap: IndexedColorMap = (workbook as XSSFWorkbook).stylesSource.indexedColors
    var color = XSSFColor(IndexedColors.RED, colorMap).indexed
    cellStyle.fillForegroundColor = color
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

    //Apply font style on cell text
    val whiteFont = workbook.createFont()
    color = XSSFColor(IndexedColors.WHITE, colorMap).indexed
    whiteFont.color = color
    whiteFont.bold = true
    cellStyle.setFont(whiteFont)


    return cellStyle
}


private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
    //setHeaderStyle is a custom function written below to add header style
    val row = sheet.createRow(0)
    val HEADER_NAME = "Data"
    val columnWidth = (15 * 500)
    sheet.setColumnWidth(0, columnWidth)
    val cell = row.createCell(0)
    cell?.setCellValue(HEADER_NAME)
    cell.cellStyle = cellStyle
}

/*
private fun addData(
rowIndex: Int,
sheet: Sheet,
newQuestion: MyQuestion,
options: List<String>,
profile: Profile
) {
//Create row based on row index
val row = sheet.createRow(rowIndex)
//Add data to each cell
createCell(row, 0, newQuestion.mainQuestion) //Column 1
createCell(row, 1, profile.postalCode) //Column 2
createCell(row, 2, profile.yearBorn) //Column 3
createCell(row, 3, profile.monthBorn) //Column 3
createCell(row, 4, profile.dayBorn) //Column 3
createCell(row, 5, profile.gender) //Column 3
createCell(row, 6, options.toString()) //Column 3
createCell(row, 7, false) //Column 3
}
*/

private fun addData(
    rowIndex: Int,
    sheet: Sheet,
    newQuestion: MyQuestion,
    options: List<String>,
    profile: Profile
) {
    // Create a new row
    val row = sheet.createRow(rowIndex)
    row.createCell(0).setCellValue(newQuestion.mainQuestion)
    row.createCell(1).setCellValue(profile.postalCode)
    row.createCell(2).setCellValue(profile.yearBorn)
    row.createCell(3).setCellValue(profile.monthBorn)
    row.createCell(4).setCellValue(profile.dayBorn)
    row.createCell(5).setCellValue(profile.gender)
    row.createCell(6).setCellValue(options.toString())
    row.createCell(7).setCellValue(false)

}

private fun createCell(row: Row, columnIndex: Int, value: String?) {
    val cell = row.createCell(columnIndex)
    cell?.setCellValue(value)


}

private fun createCell(row: Row, columnIndex: Int, value: Boolean) {
    val cell = row.createCell(columnIndex)
    cell?.setCellValue(value)

}


@SuppressLint("QueryPermissionsNeeded")
private fun createExcel(workbook: Workbook, context: Context) {


    val appDirectory = context.getExternalFilesDir("Sensimate")
    val downloadDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)


    if (downloadDirectory != null && !downloadDirectory.exists()) {
        downloadDirectory.mkdirs()
    }


    val excelFile = File(downloadDirectory, "surveyResult2.xlsx")


    try {

    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val fileOut = FileOutputStream(excelFile)
    workbook.write(fileOut)
    fileOut.close()
}



data class Question2(val mainQuestion: String, val oneChoice: Boolean, val answer: String)


object OurCalendar {
    fun getMonthName(month: Int): String? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    }
}

