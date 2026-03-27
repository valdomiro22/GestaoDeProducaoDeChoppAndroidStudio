package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import android.net.Uri
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.StorageRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class UpdatePhotoProfileUseCase @Inject constructor(
    private val usuarioFirestoreRepository: UsuarioFirestoreRepository,
    private val storageRepository: StorageRepository,
) {

    val diretorioUsuarios = "usuarios"

    suspend operator fun invoke(uid: String, fileUri: Uri): Result<Unit> {
        return try {
            val usuarioResult = usuarioFirestoreRepository.getUser(uid)
            val usuario = usuarioResult.getOrNull()
                ?: return Result.failure(Exception("Usuario não encontrado"))

            if (usuario.urlFotoPerfil.isNotBlank()) {
                storageRepository.deleteFile(usuario.urlFotoPerfil)
            }

            val destino = "$diretorioUsuarios/$uid"
            val urlImage = storageRepository.uploadFile(destino, fileUri).getOrThrow()

            val usuarioAtualizado = usuario.copy(urlFotoPerfil = urlImage)

            return usuarioFirestoreRepository.updateUser(uid, usuarioAtualizado)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}