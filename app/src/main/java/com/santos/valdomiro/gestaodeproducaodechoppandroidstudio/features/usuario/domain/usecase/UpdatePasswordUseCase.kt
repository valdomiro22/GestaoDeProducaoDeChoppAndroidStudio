package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(newPassword: String, currentPassword: String): Result<Unit> {
        return authRepository.updatePassword(
            newPassword = newPassword,
            currentPassword = currentPassword
        )
    }
}