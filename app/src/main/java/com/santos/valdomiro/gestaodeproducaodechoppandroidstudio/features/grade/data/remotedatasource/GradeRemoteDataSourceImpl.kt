package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.AcessoNegadoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.NaoEncontradoException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions.ServicoIndisponivelException
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GradeRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GradeRemoteDataSource {

    private val gradeCollection = "grade"

    override suspend fun insertGrade(grade: GradeDto) {
        mapearExecution {
            if (grade.id.isNullOrEmpty()) {
                throw IllegalArgumentException("Erro: Tentativa de salvar Grade sem ID")
            }

            firestore.collection(gradeCollection)
                .document(grade.id)
                .set(grade)
                .await()
        }
    }

    override suspend fun updateGrade(id: String, grade: GradeDto) {
        mapearExecution {
            firestore.collection(gradeCollection)
                .document(id)
                .update(grade.toMap())
                .await()
        }
    }

    override suspend fun getGrade(id: String): GradeDto? {
        return mapearExecution {
            val snapshot = firestore.collection(gradeCollection)
                .document(id)
                .get()
                .await()

            snapshot.toObject(GradeDto::class.java)
        }
    }

    override suspend fun deleteGrade(id: String) {
        mapearExecution {
            firestore.collection(gradeCollection)
                .document(id)
                .delete()
                .await()
        }
    }

    override suspend fun getAllGrades(): List<GradeDto> {
        return mapearExecution {
            val snapshot = firestore.collection(gradeCollection)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(GradeDto::class.java) }
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