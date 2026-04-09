package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class GradeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GradeRepository {

    override suspend fun insertGrade(grade: GradeEntity): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateGrade(id: String, grade: GradeEntity): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getGrade(id: String): Result<GradeEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGrade(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGrades(): Result<List<GradeEntity>> {
        TODO("Not yet implemented")
    }
}