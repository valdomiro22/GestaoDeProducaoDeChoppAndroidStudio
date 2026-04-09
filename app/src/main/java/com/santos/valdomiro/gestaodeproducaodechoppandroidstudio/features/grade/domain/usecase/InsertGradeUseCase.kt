package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.repository.GradeRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class InsertGradeUseCase @Inject constructor(
    private val repository: GradeRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(grade: GradeEntity): Result<Unit> {
        val idGerado = UUID.randomUUID().toString()
        val gradeComId = grade.copy(id = idGerado)
        return repository.insertGrade(gradeComId)
    }
}