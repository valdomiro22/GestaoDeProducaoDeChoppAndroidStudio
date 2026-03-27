package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.santos.valdomiro.gestaoproducaochopp.features.autenticacao.data.datasource.StorageDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageDataSource {

    override suspend fun uploadFile(path: String, fileUri: Uri): Result<String> {
        return try {
            val reference = storage.reference.child(path)
            reference.putFile(fileUri).await()
            val downloadUrl = reference.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDownloadUrl(fileUrl: String): Result<String> {
        return try {
            val reference = storage.getReferenceFromUrl(fileUrl)
            val downloadUrl = reference.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFile(fileUrl: String): Result<Unit> {
        return try {
            val reference = storage.getReferenceFromUrl(fileUrl)
            reference.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}