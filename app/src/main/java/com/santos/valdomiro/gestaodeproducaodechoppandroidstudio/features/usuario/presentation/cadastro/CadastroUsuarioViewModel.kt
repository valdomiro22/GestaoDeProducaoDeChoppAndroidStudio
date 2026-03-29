package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.presentation.cadastro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase.CadastrarUsuarioUseCase
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CadastroUsuarioViewModel @Inject constructor(
    private val cadastrarUsuarioUseCase: CadastrarUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CadastroUsuarioState())
    val uiState = _uiState.asStateFlow()

    fun onNomeChanged(nome: String) {
        _uiState.update { it.copy(nome = nome, erroNome = null) }
    }

    fun onSobrenomeChanged(value: String) {
        _uiState.update { it.copy(sobrenome = value, erroNome = null) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, erroEmail = null) }
    }

    fun onSenhaChanged(value: String) {
        _uiState.update { it.copy(senha = value, erroSenha = null) }
    }

    fun onConfirmarSenhaChanged(value: String) {
        _uiState.update { it.copy(confirmarSenha = value, erroConfirmarSenha = null) }
    }

    fun cadastrar() {
        val currentState = _uiState.value

        if (!validar(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erro = null) }

            val usuario = UsuarioEntity(
                nome = currentState.nome,
                sobrenome = currentState.sobrenome,
                email = currentState.email,
            )

            try {
                cadastrarUsuarioUseCase(
                    email = currentState.email,
                    password = currentState.senha,
                    usuario = usuario
                )
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erro = e.message ?: "Erro ao cadastrar usuário"
                    )
                }
            }
        }
    }

    private fun validar(state: CadastroUsuarioState): Boolean {
        var isValid = true
        var newState = state

        if (state.nome.isBlank()) {
            isValid = false
            newState = newState.copy(erroNome = "Digite o nome")
        }


        if (state.sobrenome.isBlank()) {
            isValid = false
            newState = newState.copy(erroSobrenome = "Digite o sobrenome")
        }

        if (state.email.isBlank()) {
            isValid = false
            newState = newState.copy(erroEmail = "Digite o e-mail")
        } else if (!state.email.contains("@") || !state.email.contains(".")) {
            isValid = false
            newState = newState.copy(erroEmail = "Digite um e-mail válido")
        }

        if (state.senha.isBlank()) {
            isValid = false
            newState = newState.copy(erroSenha = "Digite uma senha")
        } else if (state.senha.length < 6) {
            isValid = false
            newState = newState.copy(erroSenha = "A senha deve ter pelo menos 6 caracteres")
        }

        if (state.confirmarSenha.isBlank()) {
            isValid = false
            newState = newState.copy(erroConfirmarSenha = "Confirme a senha")
        } else if (state.confirmarSenha != state.senha) {
            isValid = false
            newState = newState.copy(erroConfirmarSenha = "As senhas não conferem")
        }

        _uiState.update { newState }
        return isValid;
    }
}