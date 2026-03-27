package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usuarioFirestoreRepository: UsuarioFirestoreRepository,
    private val deletarFotoPerfilUseCase: DeletarFotoPerfilUseCase
) {

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val uid = authRepository.getCurrentUserId()

            if (uid != null) {
                val usuario = usuarioFirestoreRepository.getUser(uid).getOrThrow()
                val urlFoto = usuario!!.urlFotoPerfil

                authRepository.deleteUser(email = email, currentPassword = password)
                usuarioFirestoreRepository.deleteUser(uid)
                deletarFotoPerfilUseCase(urlFoto)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}