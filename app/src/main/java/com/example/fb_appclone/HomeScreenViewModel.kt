package com.example.fb_appclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeScreenState{
    object Loading : HomeScreenState()
    data class loaded(
        val avatarUrl : String
    ) : HomeScreenState()

    object SignInRequired : HomeScreenState()
}
class HomeScreenViewModel : ViewModel() {

    val mutableStateFlow = MutableStateFlow<HomeScreenState>(
        HomeScreenState.Loading
    )
    val state = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
//            check for a user sign in
            mutableStateFlow.emit(
                HomeScreenState.SignInRequired
            )
        }
    }
}
