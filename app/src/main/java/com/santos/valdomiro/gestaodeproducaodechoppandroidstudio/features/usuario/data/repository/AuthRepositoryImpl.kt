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

    override suspend fun createUser(email: String, password: String): Result<String> {
        return try {
            val uid = authDataSource.createUser(email = email, password = password)
            Result.success(uid)
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(AuthException.EmailEmUso)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(AuthException.SenhaFraca)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(AuthException.EmailInvalido)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            authDataSource.login(email = email, password = password)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(AuthException.CredenciaisInvalidas)
        } catch (e: FirebaseAuthInvalidUserException) { // usuário não existe ou desativado
            Result.failure(AuthException.UsuarioNaoEncontrado)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override fun signOut(): Result<Unit> {
        return runCatching {
            authDataSource.signOut()
        }
    }

    override fun getCurrentUserId(): String? {
        return try {
            authDataSource.getCurrentUserId()
        } catch (e: Exception) {
            throw Exception("Erro ao recuperar usuario logado")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            authDataSource.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(AuthException.UsuarioNaoEncontrado)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(AuthException.EmailInvalido)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override suspend fun updateEmailAddress(newEmail: String, password: String): Result<Unit> {
        return try {
            authDataSource.updateEmailAddress(newEmail = newEmail, password = password)
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(AuthException.CredenciaisInvalidas)
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(AuthException.EmailEmUso)
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Result.failure(AuthException.ReautenticacaoNecessaria)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override suspend fun updatePassword(
        newPassword: String,
        currentPassword: String
    ): Result<Unit> {
        return try {
            authDataSource.updatePassword(
                newPassword = newPassword,
                currentPassword = currentPassword
            )
            Result.success(Unit)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(AuthException.SenhaFraca)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(AuthException.CredenciaisInvalidas)
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Result.failure(AuthException.ReautenticacaoNecessaria)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override suspend fun deleteUser(email: String, currentPassword: String): Result<Unit> {
        return try {
            authDataSource.deleteUser(email = email, currentPassword = currentPassword)
            Result.success(Unit)
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Result.failure(AuthException.ReautenticacaoNecessaria)
        } catch (e: FirebaseNetworkException) {
            Result.failure(AuthException.ErroDeRede)
        } catch (e: Exception) {
            Result.failure(AuthException.Desconhecido(e))
        }
    }

    override suspend fun getCurrentUserEmail(): String? {
        try {
            return authDataSource.getCurrentUserEmail()
        } catch (e: Exception) {
            throw Exception("Erro ao recuperar email do usuario logado")
        }
    }
}