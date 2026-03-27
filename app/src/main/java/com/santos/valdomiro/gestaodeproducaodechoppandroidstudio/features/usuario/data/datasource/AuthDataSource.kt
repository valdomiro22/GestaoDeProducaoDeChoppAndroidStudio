package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource

interface AuthDataSource {
    suspend fun createUser(email: String, password: String): String
    suspend fun login(email: String, password: String): Result<String>
    fun signOut()
    fun getCurrentUserId(): String? // Retorna o ID do usuario
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun updateEmailAddress(newEmail: String, password: String)
    suspend fun updatePassword(newPassword: String, currentPassword: String)
    suspend fun deleteUser(email: String, currentPassword: String)
    suspend fun getCurrentUserEmail(): String?
}