package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.dto.UsuarioDto

interface UsuarioRemoteDataSource {
    suspend fun insertUser(usuario: UsuarioDto)
    suspend fun updateUser(id: String, usuario: UsuarioDto)
    suspend fun getUser(id: String): UsuarioDto?
    suspend fun deleteUser(id: String)
    suspend fun getAllUsers(): List<UsuarioDto>
}