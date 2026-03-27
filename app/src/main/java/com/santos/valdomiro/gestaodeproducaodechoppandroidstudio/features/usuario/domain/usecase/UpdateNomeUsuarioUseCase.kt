package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class UpdateNomeUsuarioUseCase @Inject constructor(
    private val repository: UsuarioFirestoreRepository,
) {

    suspend operator fun invoke(
        uid: String,
        novoNome: String,
        novoSobrenome: String
    ): Result<Unit> {
        return try {
            val usuario = repository.getUser(uid).getOrThrow()

            if (usuario != null) {
                val usuarioAtualizado = usuario.copy(nome = novoNome, sobrenome = novoSobrenome)
                repository.updateUser(id = uid, usuarioAtualizado)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Usuari não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}