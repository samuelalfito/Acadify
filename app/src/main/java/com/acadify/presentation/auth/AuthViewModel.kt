package com.acadify.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acadify.model.repository.network.FireAuth
import com.acadify.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    val repository = FireAuth()
    private val _loginState = MutableSharedFlow<Resource<Unit>>()
    val loginState = _loginState.asSharedFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading(Unit))
                try {
                    val response = repository.signIn(email, password)
                    if (response.isSuccess) {
                        emit(Resource.Success(Unit))
                    } else {
                        emit(Resource.Error(response.exceptionOrNull()?.message ?: "Unknown error"))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.message ?: "Unknown error"))
                }
            }.collect {
                _loginState.emit(it)
            }
        }
    }
    
    fun register(email: String, password: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading(Unit))
                try {
                    val response = repository.register(email, password)
                    if (response.isSuccess) {
                        emit(Resource.Success(Unit))
                    } else {
                        emit(Resource.Error(response.exceptionOrNull()?.message ?: "Unknown error"))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.message ?: "Unknown error"))
                }
            }.collect {
                _loginState.emit(it)
            }
        }
    }
}