package com.questionset.repository

import com.questionset.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository:JpaRepository<Question, Int> {
}