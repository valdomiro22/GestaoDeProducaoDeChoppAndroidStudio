package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.remotedatasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.dto.ProdutoDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProdutoRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ProdutoRemoteDataSource {

    private val produtoCollection = "produto"

    override suspend fun insertProduto(produto: ProdutoDto) {
        mapearExecution {
            if (produto.id.isNullOrEmpty()) {
                throw IllegalArgumentException("Erro: Tentativa de salvar Produto sem ID")
            }

            firestore.collection(produtoCollection)
                .document(produto.id)
                .set(produto)
                .await()
        }
    }

    override suspend fun updateProduto(
        id: String,
        produto: ProdutoDto
    ) {
        mapearExecution {
            firestore.collection(produtoCollection)
                .document(id)
                .update(produto.toMap())
                .await()
        }
    }

    override suspend fun getProduto(id: String): ProdutoDto? {
        return mapearExecution {
            val snapshot = firestore.collection(produtoCollection)
                .document(id)
                .get()
                .await()

            snapshot.toObject(ProdutoDto::class.java)
        }
    }

    override suspend fun deleteProduto(id: String) {
        mapearExecution {
            firestore.collection(produtoCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun getAllProdutos(): List<ProdutoDto> {
        return mapearExecution {
            val snapshot = firestore.collection(produtoCollection)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(ProdutoDto::class.java) }
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