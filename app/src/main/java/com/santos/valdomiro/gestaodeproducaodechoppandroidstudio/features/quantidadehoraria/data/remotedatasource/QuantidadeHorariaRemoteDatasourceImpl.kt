package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.remotedatasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.dto.QuantidadeHorariaDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuantidadeHorariaRemoteDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : QuantidadeHorariaRemoteDatasource {

    val qtHorariaCollection = "quantidade_horaria"

    override suspend fun insertQtHoraria(qtHoraria: QuantidadeHorariaDto) {
        mapearExecution {
            if (qtHoraria.id.isNullOrEmpty()) {
                throw IllegalArgumentException("Erro: Tentativa de salvar QuantidadeHoraria sem ID")
            }

            firestore.collection(qtHorariaCollection)
                .document(qtHoraria.id)
                .set(qtHoraria)
                .await()
        }
    }

    override suspend fun updateQtHoraria(id: String, qtHoraria: QuantidadeHorariaDto) {
        mapearExecution {
            firestore.collection(qtHorariaCollection)
                .document(id)
                .set(qtHoraria, SetOptions.merge())
                .await()
        }
    }

    override suspend fun getQtHoraria(id: String): QuantidadeHorariaDto? {
        return mapearExecution {
            val snapshot = firestore.collection(qtHorariaCollection)
                .document(id)
                .get()
                .await()

            snapshot.toObject(QuantidadeHorariaDto::class.java)
        }
    }

    override suspend fun deleteQtHoraria(id: String) {
        mapearExecution {
            firestore.collection(qtHorariaCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun getAllQtHorarias(): List<QuantidadeHorariaDto> {
        return mapearExecution {
            val snapshot = firestore.collection(qtHorariaCollection)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(QuantidadeHorariaDto::class.java) }
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