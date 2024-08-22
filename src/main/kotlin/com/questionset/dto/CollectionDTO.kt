package com.questionset.dto

import java.sql.Timestamp

data class CollectionDTO (
    var id: Int,
    var name: String,
    var questions: List<QuestionMemberDTO>,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)

data class CollectionMemberDTO (
    var id: Int,
    var name: String,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)