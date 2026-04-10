package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeRemoteDto

interface GradeRemoteDataSource {
    suspend fun insertGrade(grade: GradeRemoteDto)
    suspend fun updateGrade(id: String, grade: GradeRemoteDto)
    suspend fun getGrade(id: String): GradeRemoteDto?
    suspend fun deleteGrade(id: String)
    suspend fun getAllGrades(): List<GradeRemoteDto>
}