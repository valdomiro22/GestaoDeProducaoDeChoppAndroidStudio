package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import javax.inject.Inject

class GetAllGradesUseCase @Inject constructor(
    private val repository: GradeRepository
) {
    suspend operator fun invoke(): Result<List<GradeEntity>> {
        return repository.getAllGrades()
    }
}