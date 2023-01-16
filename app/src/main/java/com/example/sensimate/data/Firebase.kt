package com.example.sensimate.data

//import com.example.sensimate.data.questionandsurvey.MyAnswer
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.R
import com.example.sensimate.data.Database.fetchProfile
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.ui.createEvent.docId
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            newEmail = newEmail
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

    fun unemployProfile(context: Context, email: String) {
        db.collection("users")
            .document(
                email
            ).update("isEmployee", false)
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


    suspend fun updateSurvey(context: Context, eventId: String, options: List<String>, newQuestion: MyQuestion, boolean: Boolean) {
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

                    if(boolean) {
                        scope.launch {
                            main(context = context, newQuestion, options)
                        }

                    }

                }
            }


    }

    private val REQUEST_CODE_STORAGE_PERMISSION = 1
/*
    fun requestStoragePermission(activity: Activity) :Boolean {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        return ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

 */
/*
    fun requestStoragePermission(activity: Activity) :Boolean {
        val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "The app needs storage permission to save survey results in an excel file on your device.", Toast.LENGTH_LONG).show()
                Toast.makeText(activity, "Please provide storage permission to save survey results.", Toast.LENGTH_LONG).show()
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_STORAGE_PERMISSION)
            return false
        }else{
            return true
        }
    }

 */


    fun su() {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Survey Results")
        sheet.createRow(0).createCell(0).setCellValue("Hello")
        val output = FileOutputStream("/test.xlsx")
        workbook.write(output)
        workbook.close()
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun main(context: Context, newQuestion: MyQuestion, options: List<String>) {
//        val out = FileOutputStream(File("./test_file.xlsx"))
       // val filepath = "./test_file.xlsx"
            // Creating excel workbook


            val workbook = XSSFWorkbook()

            //Creating first sheet inside workbook
            //Constants.SHEET_NAME is a string value of sheet name
            val sheet: Sheet = workbook.createSheet("Survey Results")

            //Create Header Cell Style
            val cellStyle = getHeaderStyle(workbook)

            //Creating sheet header row
            createSheetHeader(cellStyle, sheet)

        val profile = fetchProfile()!!

            //Adding data to the sheet
            addData(0, sheet, newQuestion = newQuestion, options, profile)

        createExcel(workbook,context)
        /*
        try {
            val xlWb = XSSFWorkbook()
            //Instantiate Excel worksheet:
            val xlWs = xlWb.createSheet()
            //Row index specifies the row in the worksheet (starting at 0):
            val rowNumber = 0
            //Cell index specifies the column within the chosen row (starting at 0):
            val columnNumber = 0
            //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
            val xlRow = xlWs.createRow(rowNumber)
            val xlCol = xlRow.createCell(columnNumber)
            xlCol.setCellValue("Test")
            //Write file:
            val outputStream = FileOutputStream(File("./test_file.xlsx"))
            xlWb.write(outputStream)
            xlWb.close()
        }
        catch (E:Exception) {
            Log.d("TESTTEST","EXCEL")

         */
    }
        //Instantiate Excel workbook:

    }


private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
    //setHeaderStyle is a custom function written below to add header style

    //Create sheet first row
    val row = sheet.createRow(0)

    //Header list
    val HEADER_LIST = listOf("mainQuestion", "postalCode", "yearBorn","monthBorn", "dayBorn", "gender", "answer","isEmployee")

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
    createCell(row, 7, false ) //Column 3



}




private fun createCell(row: Row, columnIndex: Int, value: String?) {
    val cell = row.createCell(columnIndex)
    cell?.setCellValue(value)

   // val headerRow = sheet.createRow(0)
    /*headerRow.createCell(0).setCellValue("mainQuestion")
    headerRow.createCell(1).setCellValue("postalCode")
    headerRow.createCell(2).setCellValue("yearBorn")
    headerRow.createCell(3).setCellValue("monthBorn")
    headerRow.createCell(4).setCellValue("dayBorn")
    headerRow.createCell(5).setCellValue("gender")
    headerRow.createCell(6).setCellValue("answer")
    headerRow.createCell(7).setCellValue("isEmployee")

     */

}

private fun createCell(row: Row, columnIndex: Int, value: Boolean) {
    val cell = row.createCell(columnIndex)
    cell?.setCellValue(value)

}


@SuppressLint("QueryPermissionsNeeded")
private fun createExcel(workbook: Workbook, context: Context) {

    //Get App Director, APP_DIRECTORY_NAME is a string
    val appDirectory = context.getExternalFilesDir("Sensimate")

    //Check App Directory whether it exists or not, create if not.
    if (appDirectory != null && !appDirectory.exists()) {
        appDirectory.mkdirs()
    }

    //Create excel file with extension .xlsx
    val excelFile = File(appDirectory,"survey.xlsx")

    //Write workbook to file using FileOutputStream
    try {

    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val fileOut = FileOutputStream(excelFile)
    workbook.write(fileOut)
    fileOut.close()

/*
    val intent = Intent(Intent.ACTION_VIEW)
    val uri: Uri = FileProvider.getUriForFile(
        context, context.applicationContext.packageName + "com.example.file-provider", excelFile)
    intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(intent)

 */
/*
    val activity = currentActivity.value
    val file = File("survey_results.xlsx")
    val contentUri = FileProvider.getUriForFile(activity, "com.example.fileprovider", file)
    val openFileIntent = Intent(Intent.ACTION_VIEW)
    openFileIntent.setDataAndType(contentUri, "application/vnd.ms-excel")
    openFileIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    openFileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    activity?.startActivity(openFileIntent)

 */
/*
    val contentUri = FileProvider.getUriForFile(context, "com.example.file-provider", excelFile)
    val openFileIntent = Intent(Intent.ACTION_VIEW)
    openFileIntent.setDataAndType(contentUri, "application/vnd.ms-excel")
    openFileIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    context.startActivity(openFileIntent)

 */
/*
    val file = File(context.getExternalFilesDir(null), "test_file.xlsx")
    val out = FileOutputStream(file)
    workbook.write(out)
    out.close()

 */
/*
    val contentUri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".file-provider", excelFile)
    val packageManager = context.packageManager
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(contentUri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if(list.size > 0) {
        context.startActivity(intent)
    } else {
        // Show message to user that no app can open the file
        Toast.makeText(context, "No app found to open this file type, " +
                "Would you like to download a spreadsheet app?", Toast.LENGTH_SHORT).show()
        val downloadAppIntent = Intent(Intent.ACTION_VIEW)

 */
    val contentUri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".file-provider", excelFile)
    val packageManager = context.packageManager
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(contentUri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    if(list.size > 0) {
        context.startActivity(intent)
    } else {
        // Show message to user that no app can open the file
        /*Toast.makeText(context, "No app found to open this file type, " +
                "Would you like to download a spreadsheet app?", Toast.LENGTH_SHORT).show()
        val downloadAppIntent = Intent(Intent.ACTION_VIEW)
        downloadAppIntent.data = Uri.parse("https://play.google.com/store/search?q=spreadsheet&c=apps")
        context.startActivity(downloadAppIntent)

         */
    }


}







/*

    suspend fun test2(context:Context, eventId: String, options: List<String>, newQuestion: MyQuestion) {
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
                    val workbook = XSSFWorkbook()
                    val sheet = workbook.createSheet("Survey Results")

                    val headerRow = sheet.createRow(0)
                    headerRow.createCell(0).setCellValue("mainQuestion")
                    headerRow.createCell(1).setCellValue("postalCode")
                    headerRow.createCell(2).setCellValue("yearBorn")
                    headerRow.createCell(3).setCellValue("monthBorn")
                    headerRow.createCell(4).setCellValue("dayBorn")
                    headerRow.createCell(5).setCellValue("gender")
                    headerRow.createCell(6).setCellValue("answer")
                    headerRow.createCell(7).setCellValue("isEmployee")

                    val dataRow = sheet.createRow(1)
                    dataRow.createCell(0).setCellValue(newQuestion.mainQuestion)
                    dataRow.createCell(1).setCellValue(profile.postalCode)
                    dataRow.createCell(2).setCellValue(profile.yearBorn)
                    dataRow.createCell(3).setCellValue(profile.monthBorn)
                    dataRow.createCell(4).setCellValue(profile.dayBorn)
                    dataRow.createCell(5).setCellValue(profile.gender)
                    dataRow.createCell(6).setCellValue(options.toString())
                    dataRow.createCell(7).setCellValue(false)
                    if(requestStoragePermission(context as Activity)){
                        val folder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"ExcelExport")
                        if (!folder.exists()) {
                            if(folder.mkdirs()){
                                val file = File(folder,"survey_results.xlsx")
                                try {
                                    val outputStream = FileOutputStream(file)
                                    workbook.write(outputStream)
                                    outputStream.close()
                                    Toast.makeText(context, "File saved successfully at ${folder.path}", Toast.LENGTH_LONG).show()
                                } catch (e: IOException) {
                                    Toast.makeText(context, "Error saving file: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }else{
                                Toast.makeText(context, "Error creating directory", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            val file = File(folder,"survey_results.xlsx")
                            try {
                                val outputStream = FileOutputStream(file)
                                workbook.write(outputStream)
                                outputStream.close()
                                Toast.makeText(context, "File saved successfully at ${folder.path}", Toast.LENGTH_LONG).show()
                            } catch (e: IOException) {
                                Toast.makeText(context, "Error saving file: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(context, "Permission denied, please provide storage permission", Toast.LENGTH_LONG).show()
                    }
                }
            }


    }

 */


    suspend fun updateSurvey2(eventId: String, options: List<String>, newQuestion: MyQuestion) {

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

                    val optionsString = options.joinToString(",")
                    exportToExcel(
                        postalCode = profile.postalCode,
                        yearBorn = profile.yearBorn.toInt(),
                        monthBorn = profile.monthBorn.toInt(),
                        dayBorn = profile.dayBorn.toInt(),
                        gender = profile.gender,
                        answer = optionsString,
                        isEmployee = false
                    )


                }
            }
    }

                    // Create a new Excel workbook
/*
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
                    val excelExportFolder =
                        File(Environment.getExternalStorageDirectory(), "ExcelExport")
                    if (!excelExportFolder.exists()) {
                        excelExportFolder.mkdir()
                    }

                    val file = File(excelExportFolder, "survey_results.xlsx")
                    val fileOut = FileOutputStream(file)
                    workbook.write(fileOut)
                    fileOut.close()

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

    private fun exportToExcel(
        postalCode: String,
        yearBorn: Int,
        monthBorn: Int,
        dayBorn: Int,
        gender: String,
        answer: String,
        isEmployee: Boolean
    ) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Survey")

        // Create the headers
        val headers = arrayOf(
            "postalCode",
            "yearBorn",
            "monthBorn",
            "dayBorn",
            "gender",
            "answer",
            "isEmployee"
        )
        val row = sheet.createRow(0)
        for (i in headers.indices) {
            val cell = row.createCell(i)
            cell.setCellValue(headers[i])
        }

        // Create the data row
        val dataRow = sheet.createRow(1)
        dataRow.createCell(0).setCellValue(postalCode)
        dataRow.createCell(1).setCellValue(yearBorn.toString())
        dataRow.createCell(2).setCellValue(monthBorn.toString())
        dataRow.createCell(3).setCellValue(dayBorn.toString())
        dataRow.createCell(4).setCellValue(gender)
        dataRow.createCell(5).setCellValue(answer)
        dataRow.createCell(6).setCellValue(isEmployee.toString())


        // Write the workbook to a file
        val file = File("survey.xlsx")
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
    }


    fun excel2() {

    }

/*

    private fun exportToExcel(survey: HashMap<String, Any>) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Survey")

        // Create the headers
        val headers = arrayOf(
            "postalCode",
            "yearBorn",
            "monthBorn",
            "dayBorn",
            "gender",
            "answer",
            "isEmployee"
        )
        val row = sheet.createRow(0)
        for (i in headers.indices) {
            val cell = row.createCell(i)
            cell.setCellValue(headers[i])
        }

        // Create the data rows
        val dataRow = sheet.createRow(1)
        dataRow.createCell(0).setCellValue(survey["postalCode"] as String)
        dataRow.createCell(1).setCellValue(survey["yearBorn"].toString())
        dataRow.createCell(2).setCellValue(survey["monthBorn"].toString())
        dataRow.createCell(3).setCellValue(survey["dayBorn"].toString())
        dataRow.createCell(4).setCellValue(survey["gender"] as String)
        dataRow.createCell(5).setCellValue(survey["answer"] as String)
        dataRow.createCell(6).setCellValue(survey["isEmployee"] as Boolean)


        // Write the workbook to a file
        val file = File("survey.xlsx")
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
    }

 */




    //TODO: LATER


    data class Question2(val mainQuestion: String, val oneChoice: Boolean, val answer: String)


    object OurCalendar {
        fun getMonthName(month: Int): String? {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, month)
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        }
    }

