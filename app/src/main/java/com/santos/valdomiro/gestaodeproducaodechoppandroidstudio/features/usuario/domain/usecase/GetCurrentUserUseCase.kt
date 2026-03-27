package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usuarioFirestoreRepository: UsuarioFirestoreRepository
) {

    suspend operator fun invoke(): Result<UsuarioEntity?> {
        return try {
            val uid = authRepository.getCurrentUserId()

            if (uid != null) {
                usuarioFirestoreRepository.getUser(uid)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao buscar usuario"))
        }
    }

}