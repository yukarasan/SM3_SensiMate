package com.example.sensimate.data

//import com.example.sensimate.data.questionandsurvey.MyAnswer
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.R
import com.example.sensimate.data.Database.fetchListOfEvents
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.ui.createEvent.docId
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.jar.Manifest


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
     * Firestore database.
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
     * @param postalCode The postal code of the user
     * @param yearBorn the year of birth of the user
     * @param monthBorn the month of birth of the user
     * @param dayBorn the day of birth of the user
     * @param gender the gender of the user
     * @param newEmail the new email for the user
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
            newEmail = newEmail
        )
    }

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
            "isEmployee" to false,
            "isAdmin" to false
        )

        db.collection("users").document(auth.currentUser?.email.toString())
            .set(profile)
    }


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

    fun textAnswer(question: String) {
        val mainQuest = hashMapOf(
            "mainQuestion" to question
        )
        val subcollectionRef = db.collection("events").document(docId).collection("questions")
        subcollectionRef.add(mainQuest)
    }//TODO: Ahmad

    fun editEvent() {} //TODO: Sabirin

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

/*
    fun updateSurvey(eventId: String, survey: List<MyAnswer>) { //TODO: Ansh og (Hussein?)
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

 */


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


    suspend fun updateSurvey(eventId: String, options: List<String>, newQuestion: MyQuestion) {
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


        val questionRef = db.collection("events").document(eventId)
            .collection("Answers").add(test).addOnSuccessListener { docRef ->
                docRef.collection("users").add(survey).addOnSuccessListener { docRef ->

                }

            }


    }




    suspend fun updateSurvey2(eventId: String, options: List<String>, newQuestion: MyQuestion) {
        // ... your existing code here ...

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

        val questionRef = db.collection("events").document(eventId)
            .collection("Answers").add(test).addOnSuccessListener { docRef ->
                docRef.collection("users").add(survey).addOnSuccessListener { docRef ->
                    // Create a new Excel workbook
                    val workbook = XSSFWorkbook()
                    // Create a new sheet in the workbook
                    val sheet = workbook.createSheet("Survey Results")


                    // Add the data to the sheet
                    var rowNum = 0
                    val row = sheet.createRow(rowNum++)
                    row.createCell(0).setCellValue("Postal Code")
                    row.createCell(1).setCellValue(survey["postalCode"].toString())
                    val row2 = sheet.createRow(rowNum++)
                    row2.createCell(0).setCellValue("Year Born")
                    row2.createCell(1).setCellValue(survey["yearBorn"].toString())
                    val row3 = sheet.createRow(rowNum++)
                    row3.createCell(0).setCellValue("Month Born")
                    row3.createCell(1).setCellValue(survey["monthBorn"].toString())
                    val row4 = sheet.createRow(rowNum++)
                    row4.createCell(0).setCellValue("Day Born")
                    row4.createCell(1).setCellValue(survey["dayBorn"].toString())
                    val row5 = sheet.createRow(rowNum++)
                    row5.createCell(0).setCellValue("Gender")
                    row5.createCell(1).setCellValue(survey["gender"].toString())
                    val row6 = sheet.createRow(rowNum++)
                    row6.createCell(0).setCellValue("Answer")
                    row6.createCell(1).setCellValue(survey["answer"].toString())
                    val row7 = sheet.createRow(rowNum++)
                    row7.createCell(0).setCellValue("Is Employee")
                    row7.createCell(1).setCellValue(survey["isEmployee"].toString())



                    // Write the workbook to a file
                    val excelExportFolder = File(Environment.getExternalStorageDirectory(), "ExcelExport")
                    if (!excelExportFolder.exists()) {
                        excelExportFolder.mkdir()
                    }

                    val file = File(excelExportFolder, "survey_results.xlsx")
                    val fileOut = FileOutputStream(file)
                    workbook.write(fileOut)
                    fileOut.close()


                }
            }
    }



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
