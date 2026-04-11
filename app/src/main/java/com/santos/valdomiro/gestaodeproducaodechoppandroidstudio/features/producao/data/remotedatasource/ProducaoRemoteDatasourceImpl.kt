package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.remotedatasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.dto.ProducaoRemoteDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProducaoRemoteDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ProducaoRemoteDatasource {

    val producaoCollection = "producao"

    override suspend fun insertProducao(producao: ProducaoRemoteDto) {
        mapearExecution {
            if (producao.id.isNullOrEmpty()) {
                throw IllegalArgumentException("Erro: Tentativa de salvar Producao sem ID")
            }

            firestore.collection(producaoCollection)
                .document(producao.id)
                .set(producao)
                .await()
        }
    }

    override suspend fun updateProducao(
        id: String,
        producao: ProducaoRemoteDto
    ) {
        mapearExecution {
            firestore.collection(producaoCollection)
                .document(id)
                .set(producao, SetOptions.merge())
                .await()
        }
    }

    override suspend fun getProducao(id: String): ProducaoRemoteDto? {
        return mapearExecution {
            val snapshot = firestore.collection(producaoCollection)
                .document(id)
                .get()
                .await()

            snapshot.toObject(ProducaoRemoteDto::class.java)
        }
    }

    override suspend fun deleteProducao(id: String) {
        mapearExecution {
            firestore.collection(producaoCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun getAllProducoes(): List<ProducaoRemoteDto> {
        return mapearExecution {
            val snapshot = firestore.collection(producaoCollection)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(ProducaoRemoteDto::class.java) }
        }
    }

    /**
     * Função auxiliar para centralizar o tratamento de erros do Firebase.
     * Ela "traduz" exceções técnicas para exceções de domínio.
     */
    private suspend fun <T> mapearExecution(action: suspend () -> T): T {
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