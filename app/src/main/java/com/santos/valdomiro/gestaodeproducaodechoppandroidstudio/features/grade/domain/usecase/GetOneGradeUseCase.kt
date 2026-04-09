package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class GetOneGradeUseCase @Inject constructor(
    private val repository: GradeRepository
) {
    suspend operator fun invoke(id: String): Result<GradeEntity> {
        return repository.getGrade(id).fold(
            onSuccess = { grade ->
                if (grade != null) {
                    Result.success(grade)
                } else {
                    Result.failure(Exception("Grade não encontrada com o ID: $id"))
                }
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}