package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class UpdateGradeUseCase @Inject constructor(
    private val repository: GradeRepository
) {
    suspend operator fun invoke(id: String, grade: GradeEntity): Result<Unit> {
        return repository.updateGrade(id, grade)
    }
}