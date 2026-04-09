package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper.toDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource.GradeRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class GradeRepositoryImpl @Inject constructor(
    private val dataSource: GradeRemoteDataSource
) : GradeRepository {

    override suspend fun insertGrade(grade: GradeEntity): Result<Unit> {
        return  try {
            val dto = grade.toDto()
            dataSource.insertGrade(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateGrade(id: String, grade: GradeEntity): Result<Unit> {
        return try {
            val dto = grade.toDto()
            dataSource.updateGrade(id, dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGrade(id: String): Result<GradeEntity?> {
        return try {
            val dto = dataSource.getGrade(id)
            val entity = dto?.toEntity()
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGrade(id: String): Result<Unit> {
        return try {
            dataSource.deleteGrade(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllGrades(): Result<List<GradeEntity>> {
        return try {
            val dtos = dataSource.getAllGrades()
            val entities = dtos.map { it.toEntity() }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}