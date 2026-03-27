package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.UsuarioRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.dto.UsuarioDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsuarioRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : UsuarioRemoteDataSource {
    val usuarioCollection = "usuarios"

    override suspend fun insertUser(usuario: UsuarioDto) {
        if (usuario.id.isBlank()) {
            throw IllegalArgumentException("Erro: Tentativa de salvar usuario sem UID do Firebase Auth")
        }

        firestore
            .collection(usuarioCollection)
            .document(usuario.id)
            .set(usuario)
            .await()

    }

    override suspend fun updateUser(id: String, usuario: UsuarioDto) {
        if (id.isBlank()) {
            throw IllegalArgumentException("ID do usuário não pode ser vazio")
        }

        firestore
            .collection(usuarioCollection)
            .document(id)
            .set(
                usuario,
                SetOptions.merge()
            )  // merge() → atualiza só os campos existentes, não apaga os outros
            .await()
    }

    override suspend fun getUser(id: String): UsuarioDto? {
        if (id.isBlank()) return null

        val snapshot = firestore
            .collection(usuarioCollection)
            .document(id)
            .get()
            .await()

        return snapshot.toObject(UsuarioDto::class.java)
    }

    override suspend fun deleteUser(id: String) {
        if (id.isBlank()) {
            throw IllegalArgumentException("ID do usuário não pode ser vazio")
        }

        firestore
            .collection(usuarioCollection)
            .document(id)
            .delete()
            .await()
    }

    override suspend fun getAllUsers(): List<UsuarioDto> {
        val snapshot = firestore
            .collection(usuarioCollection)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(UsuarioDto::class.java)
        }
    }
}