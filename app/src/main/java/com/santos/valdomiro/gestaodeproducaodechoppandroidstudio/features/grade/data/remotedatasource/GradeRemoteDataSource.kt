package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeDto

interface GradeRemoteDataSource {
    suspend fun insertGrade(grade: GradeDto)
    suspend fun updateGrade(id: String, grade: GradeDto)
    suspend fun getGrade(id: String): GradeDto?
    suspend fun deleteGrade(id: String)
    suspend fun getAllGrades(): List<GradeDto>
}