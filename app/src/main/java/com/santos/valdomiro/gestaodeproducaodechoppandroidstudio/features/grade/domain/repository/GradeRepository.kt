package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity

interface GradeRepository {
    suspend fun insertGrade(grade: GradeEntity): Result<Unit>
    suspend fun updateGrade(id: String, grade: GradeEntity): Result<Unit>
    suspend fun getGrade(id: String): Result<GradeEntity?>
    suspend fun deleteGrade(id: String): Result<Unit>
    suspend fun getAllGrades(): Result<List<GradeEntity>>
}