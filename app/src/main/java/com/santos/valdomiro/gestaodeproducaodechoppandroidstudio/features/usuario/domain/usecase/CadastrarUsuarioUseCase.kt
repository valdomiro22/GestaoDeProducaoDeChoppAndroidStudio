package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository.AuthRepositoryImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository.UsuarioFirestoreFirestoreRepositoryImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity
import javax.inject.Inject

class CadastrarUsuarioUseCase @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val usuarioRepository: UsuarioFirestoreFirestoreRepositoryImpl
) {

    suspend operator fun invoke(
        usuario: UsuarioEntity,
        password: String,
        email: String
    ): Result<String> {
        return runCatching {
            val novoId = authRepository.createUser(email, password).getOrThrow()
            val usuarioConId = usuario.copy(id = novoId)
            usuarioRepository.insertUser(usuarioConId)
            novoId
        }
    }
}