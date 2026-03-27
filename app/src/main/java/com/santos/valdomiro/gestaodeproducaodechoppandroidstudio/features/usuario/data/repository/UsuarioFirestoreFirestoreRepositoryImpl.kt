package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.UsuarioRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.mapper.toDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.util.TAG
import javax.inject.Inject

class UsuarioFirestoreFirestoreRepositoryImpl @Inject constructor(
    private val usuarioDataSource: UsuarioRemoteDataSource
) : UsuarioFirestoreRepository {

    override suspend fun insertUser(usuario: UsuarioEntity): Result<Unit> {
        return try {
            usuarioDataSource.insertUser(usuario.toDto())
            Result.success(Unit)
        } catch (e: FirebaseFirestoreException) {
            val exceptionMapeada = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    AcessoNegadoException(e)

                FirebaseFirestoreException.Code.NOT_FOUND ->
                    NaoEncontradoException(e)

                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    ServicoIndisponivelException(e)

                else ->
                    ErroBancoDadosDesconhecidoException(e)
            }
            Result.failure(exceptionMapeada)
        } catch (e: Exception) {
            Result.failure(ErroBancoDadosDesconhecidoException(e))
        }
    }

    override suspend fun updateUser(id: String, usuario: UsuarioEntity): Result<Unit> {
        return try {
            usuarioDataSource.updateUser(id, usuario.toDto())
            Log.d(TAG, "Update usuario chamado")
            Result.success(Unit)
        } catch (e: FirebaseFirestoreException) {
            val exceptionMapeada = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> AcessoNegadoException(e)
                FirebaseFirestoreException.Code.NOT_FOUND -> NaoEncontradoException(e)
                FirebaseFirestoreException.Code.UNAVAILABLE -> ServicoIndisponivelException(e)
                else -> ErroBancoDadosDesconhecidoException(e)
            }
            Result.failure(exceptionMapeada)
        } catch (e: Exception) {
            Result.failure(ErroBancoDadosDesconhecidoException(e))
        }
    }

    override suspend fun getUser(id: String): Result<UsuarioEntity?> {
        return try {
            val usuarioDocument = usuarioDataSource.getUser(id)
            Result.success(usuarioDocument?.toEntity())
        } catch (e: FirebaseFirestoreException) {
            val exceptionMapeada = when (e.code) {
                FirebaseFirestoreException.Code.NOT_FOUND -> NaoEncontradoException(e)
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> AcessoNegadoException(e)
                FirebaseFirestoreException.Code.UNAVAILABLE -> ServicoIndisponivelException(e)
                else -> ErroBancoDadosDesconhecidoException(e)
            }
            Result.failure(exceptionMapeada)
        } catch (e: Exception) {
            Result.failure(ErroBancoDadosDesconhecidoException(e))
        }
    }

    override suspend fun deleteUser(id: String): Result<Unit> {
        return try {
            usuarioDataSource.deleteUser(id)
            Result.success(Unit)
        } catch (e: FirebaseFirestoreException) {
            val exceptionMapeada = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> AcessoNegadoException(e)
                FirebaseFirestoreException.Code.NOT_FOUND -> NaoEncontradoException(e) // usuário já não existe
                FirebaseFirestoreException.Code.UNAVAILABLE -> ServicoIndisponivelException(e)
                else -> ErroBancoDadosDesconhecidoException(e)
            }
            Result.failure(exceptionMapeada)
        } catch (e: Exception) {
            Result.failure(ErroBancoDadosDesconhecidoException(e))
        }

    }

    override suspend fun getAllUsers(): Result<List<UsuarioEntity>> {
        return try {
            val listaDocument = usuarioDataSource.getAllUsers()
            val listaModel = listaDocument.map { it.toEntity() }
            Result.success(listaModel)
        } catch (e: FirebaseFirestoreException) {
            val exceptionMapeada = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> AcessoNegadoException(e)
                FirebaseFirestoreException.Code.UNAVAILABLE -> ServicoIndisponivelException(e)
                else -> ErroBancoDadosDesconhecidoException(e)
            }
            Result.failure(exceptionMapeada)
        } catch (e: Exception) {
            Result.failure(ErroBancoDadosDesconhecidoException(e))
        }
    }
}