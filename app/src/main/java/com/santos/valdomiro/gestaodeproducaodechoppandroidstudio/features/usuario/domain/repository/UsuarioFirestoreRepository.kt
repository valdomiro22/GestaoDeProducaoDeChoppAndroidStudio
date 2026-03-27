package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity


interface UsuarioFirestoreRepository {
    suspend fun insertUser(usuario: UsuarioEntity): Result<Unit>
    suspend fun updateUser(id: String, usuario: UsuarioEntity): Result<Unit>
    suspend fun getUser(id: String): Result<UsuarioEntity?>
    suspend fun deleteUser(id: String): Result<Unit>
    suspend fun getAllUsers(): Result<List<UsuarioEntity>>
}