package com.questionset.repository

import com.questionset.model.QuestionStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface QuestionStatusRepository: JpaRepository<QuestionStatus, Int> {
    fun findFirstByName(name:String): Optional<QuestionStatus>
}