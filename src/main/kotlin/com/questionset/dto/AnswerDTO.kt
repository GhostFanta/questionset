package com.questionset.dto

import java.sql.Timestamp

data class AnswerDTO (
    var id: Int,
    var content: String,
    var selected: Boolean,
    var question: QuestionMemberDTO,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)

data class AnswerDetailDTO(
    var id: Int,
    var content: String,
    var selected: Boolean,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)

data class AnswerMemberDTO (
    var id: Int,
    var content: String,
    var selected: Boolean,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)