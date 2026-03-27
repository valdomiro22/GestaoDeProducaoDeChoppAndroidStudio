package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.AuthDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthDataSource {

    override suspend fun createUser(email: String, password: String): String {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val user =
            authResult?.user ?: throw Exception("Erro no Firebase Auth: Usuario nulo apos criacao.")
        return user.uid;
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return runCatching {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.uid ?: throw Exception("Usuário nulo após login")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun updateEmailAddress(newEmail: String, password: String) {
        val user = auth.currentUser ?: throw Exception("Nenhum usuário logado")

        val credential = EmailAuthProvider
            .getCredential(user.email!!, password)
        user.reauthenticate(credential).await()

        user.verifyBeforeUpdateEmail(newEmail).await()
        user.reload().await()
    }

    override suspend fun updatePassword(newPassword: String, currentPassword: String) {
        val user = auth.currentUser ?: throw Exception("Nenhum usuário logado")
        val email = user.email ?: throw Exception("Email não encontrado")

        val credential = EmailAuthProvider.getCredential(email, currentPassword)

        user.reauthenticate(credential).await()
        user.updatePassword(newPassword).await()

        user.getIdToken(true).await()
    }

    override suspend fun deleteUser(email: String, currentPassword: String) {
        val user = auth.currentUser ?: throw Exception("Nenhum usuário logado")

        val credential = EmailAuthProvider
            .getCredential(user.email!!, currentPassword)

        user.reauthenticate(credential).await()
        user.delete().await()
    }

    override suspend fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

}