package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.dto.BarrilDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BarrilRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : BarrilRemoteDataSource {

    private val barrilCollection = "barris"

    override suspend fun insertBarril(barril: BarrilDto) {
        handleExecution {
            if (barril.id.isNullOrEmpty()) {
                throw IllegalArgumentException("Erro: Tentativa de salvar Barril sem ID")
            }

            firestore.collection(barrilCollection)
                .document(barril.id)
                .set(barril)
                .await()
        }
    }

    override suspend fun updateBarril(id: String, barril: BarrilDto) {
        handleExecution {
            firestore.collection(barrilCollection)
                .document(id)
                .update(barril.toMap())
                .await()
        }
    }

    override suspend fun getBarril(id: String): BarrilDto? {
        return handleExecution {
            val snapshot = firestore.collection(barrilCollection)
                .document(id)
                .get()
                .await()

            snapshot.toObject(BarrilDto::class.java)
        }
    }

    override suspend fun deleteBarril(id: String) {
        handleExecution {
            firestore.collection(barrilCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun getAllBarris(): List<BarrilDto> {
        return handleExecution {
            val snapshot = firestore.collection(barrilCollection)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(BarrilDto::class.java) }
        }
    }

    /**
     * Função auxiliar para centralizar o tratamento de erros do Firebase.
     * Ela "traduz" exceções técnicas para exceções de domínio.
     */
    private suspend fun <T> handleExecution(action: suspend () -> T): T {
        return try {
            action()
        } catch (e: FirebaseFirestoreException) {
            throw when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> AcessoNegadoException(e)
                FirebaseFirestoreException.Code.NOT_FOUND -> NaoEncontradoException(e)
                FirebaseFirestoreException.Code.UNAVAILABLE -> ServicoIndisponivelException(e)
                else -> ErroBancoDadosDesconhecidoException(e)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}