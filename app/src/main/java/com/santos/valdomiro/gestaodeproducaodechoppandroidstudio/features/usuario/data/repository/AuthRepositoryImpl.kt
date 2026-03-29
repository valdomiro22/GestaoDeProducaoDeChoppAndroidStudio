package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AuthException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.AuthDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    private fun mapError(e: Throwable): AuthException {
        return when (e) {
            is FirebaseAuthUserCollisionException -> AuthException.EmailEmUso
            is FirebaseAuthWeakPasswordException -> AuthException.SenhaFraca
            is FirebaseAuthInvalidCredentialsException -> AuthException.CredenciaisInvalidas
            is FirebaseAuthInvalidUserException -> AuthException.UsuarioNaoEncontrado
            is FirebaseNetworkException -> AuthException.ErroDeRede
            is FirebaseAuthRecentLoginRequiredException -> AuthException.ReautenticacaoNecessaria
            else -> AuthException.Desconhecido(e)
        }
    }

    override suspend fun createUser(email: String, password: String): Result<String> {
        val result = authDataSource.createUser(email, password)
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Result.failure(mapError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        val result = authDataSource.login(email, password)
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Result.failure(mapError(result.exceptionOrNull()!!))
        }
    }

    override fun signOut(): Result<Unit> {
        return authDataSource.signOut()
    }

    override fun getCurrentUserId(): String? = authDataSource.getCurrentUserId()

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        val result = authDataSource.sendPasswordResetEmail(email)
        return if (result.isSuccess) Result.success(Unit)
        else Result.failure(mapError(result.exceptionOrNull()!!))
    }

    override suspend fun updateEmailAddress(newEmail: String, password: String): Result<Unit> {
        val result = authDataSource.updateEmailAddress(newEmail, password)
        return if (result.isSuccess) Result.success(Unit)
        else Result.failure(mapError(result.exceptionOrNull()!!))
    }

    override suspend fun updatePassword(newPassword: String, currentPassword: String): Result<Unit> {
        val result = authDataSource.updatePassword(newPassword, currentPassword)
        return if (result.isSuccess) Result.success(Unit)
        else Result.failure(mapError(result.exceptionOrNull()!!))
    }

    override suspend fun deleteUser(email: String, currentPassword: String): Result<Unit> {
        val result = authDataSource.deleteUser(email, currentPassword)
        return if (result.isSuccess) Result.success(Unit)
        else Result.failure(mapError(result.exceptionOrNull()!!))
    }

    override suspend fun getCurrentUserEmail(): String? = authDataSource.getCurrentUserEmail()
}