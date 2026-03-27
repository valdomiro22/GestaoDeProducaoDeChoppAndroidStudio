package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(newEmail: String, currentPassword: String): Result<Unit> {
        return try {
            val result = authRepository.updateEmailAddress(newEmail, currentPassword)

            if (result.isSuccess) {
                Result.success(Unit) // Sucesso: email de verificação enviado
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}