package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(newPassword: String, currentPassword: String): Result<Unit> {
        return try {
            val result = authRepository.updatePassword(
                newPassword = newPassword,
                currentPassword = currentPassword
            )

            if (result.isSuccess) {
                Result.success(Unit) // Sucesso: email de alteração enviado
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}