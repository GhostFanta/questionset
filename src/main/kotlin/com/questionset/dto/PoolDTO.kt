package com.questionset.dto

import java.sql.Timestamp

data class PoolDTO(
    var id: Int,
    var name: String,
    var summary: String,
    var questions: List<QuestionMemberDTO>,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)


data class PoolMemberDTO(
    var id: Int,
    var name: String,
    var summary: String,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)