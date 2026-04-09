package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class DeleteGradeUseCase @Inject constructor(
    private val repository: GradeRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteGrade(id)
    }
}