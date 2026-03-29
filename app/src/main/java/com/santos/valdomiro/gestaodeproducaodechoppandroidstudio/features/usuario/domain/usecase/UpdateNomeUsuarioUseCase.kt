package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import javax.inject.Inject

class UpdateNomeUsuarioUseCase @Inject constructor(
    private val repository: UsuarioFirestoreRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        novoNome: String,
        novoSobrenome: String
    ): Result<Unit> {
        return try {
            val uid = authRepository.getCurrentUserId()
                ?: return Result.failure(Exception("Usuário não autenticado"))

            val usuario = repository.getUser(uid).getOrNull()
                ?:
                return Result.failure<Unit>(Exception("Dados do usuário não encontrados no banco"))

            val usuarioAtualizado = usuario.copy(
                nome = novoNome,
                sobrenome = novoSobrenome
            )
            repository.updateUser(id = usuario.id, usuarioAtualizado)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}