package com.questionset.dto

import java.sql.Timestamp

data class QuestionStatusDTO (
    var id: Int,
    var name: String,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)
