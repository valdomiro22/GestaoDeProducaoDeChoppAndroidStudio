package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.StorageRepository
import javax.inject.Inject

class DeletarFotoPerfilUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(urlFoto: String?): Result<Unit> {
        return try {
            if (!urlFoto.isNullOrBlank()) {
                storageRepository.deleteFile(urlFoto)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}