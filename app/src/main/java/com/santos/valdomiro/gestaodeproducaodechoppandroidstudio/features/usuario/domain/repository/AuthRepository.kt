package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository

interface AuthRepository {
    suspend fun createUser(email: String, password: String): Result<String>
    suspend fun login(email: String, password: String): Result<String>
    fun signOut() : Result<Unit>
    fun getCurrentUserId(): String? // Retorna o ID do usuario
    suspend fun sendPasswordResetEmail(email: String) : Result<Unit>
    suspend fun updateEmailAddress(newEmail: String, password: String) : Result<Unit>
    suspend fun updatePassword(newPassword: String, currentPassword: String) : Result<Unit>
    suspend fun deleteUser(email: String, currentPassword: String) : Result<Unit>
    suspend fun getCurrentUserEmail(): String?
}