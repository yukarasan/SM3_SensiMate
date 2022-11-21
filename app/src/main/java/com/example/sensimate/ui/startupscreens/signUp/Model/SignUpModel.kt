package com.example.sensimate.ui.startupscreens.signUp.Model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NewAccount(
    email: MutableState<String> = mutableStateOf(""),
    password: MutableState<String> = mutableStateOf(""),
    postalCode: MutableState<Int> = mutableStateOf(0),
    gender: MutableState<String> = mutableStateOf(""),
)