package com.acadify.presentation.navbar

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class NavBarViewModel : ViewModel() {
    val navBarState = mutableStateOf("kelola_nilai")
}