package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.GetCurrentUserUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    var isLoading = mutableStateOf(true)
        private set

//    var startDestination by mutableStateOf(Screen.HomeScreen.route)
    var startDestination by mutableStateOf(Screen.HomeScreen.route)
        private set

    init {
        verificarUsuarioLogado()
    }

    private fun verificarUsuarioLogado() {
        viewModelScope.launch {
            delay(1000)

            val result = getCurrentUserUseCase()

            result.onSuccess { usuario ->
                startDestination = if (usuario != null) {
                    Screen.HomeScreen.route
                } else {
                    Screen.LoginScreen.route
                }
            }.onFailure {
                startDestination = Screen.LoginScreen.route
            }

            isLoading.value = false
        }
    }
}