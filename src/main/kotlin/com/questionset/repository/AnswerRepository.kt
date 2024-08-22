package com.questionset.repository

import com.questionset.model.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AnswerRepository: JpaRepository<Answer, Int> {
    fun findByContent(content: String):List<Answer>
}