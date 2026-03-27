package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class SincronizarEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usuarioFirestoreRepository: UsuarioFirestoreRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        val uid = authRepository.getCurrentUserId()
            ?: return Result.failure(Exception("Usuário não logado"))

        return try {
            val usuarioResult = usuarioFirestoreRepository.getUser(uid)
            val usuarioAtual = usuarioResult.getOrNull()
                ?: return Result.failure(Exception("Usuário não encontrado no Firestore"))

            val emailNoAuth = authRepository.getCurrentUserEmail()
                ?: return Result.failure(Exception("Email não disponível no Auth"))

            if (usuarioAtual.email != emailNoAuth) {
                val usuarioAtualizado = usuarioAtual.copy(email = emailNoAuth)
                usuarioFirestoreRepository.updateUser(uid, usuarioAtualizado)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}