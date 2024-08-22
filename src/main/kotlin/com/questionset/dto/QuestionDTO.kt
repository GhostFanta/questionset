package com.questionset.dto

import java.sql.Timestamp

data class QuestionDTO (
    var id: Int,
    var content: String,
    var summary: String,
    var answers: List<AnswerMemberDTO>,
    var tags: List<TagMemberDTO>,
    var collections: List<CollectionMemberDTO>,
    var status: QuestionStatusDTO,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)

data class QuestionMemberDTO (
    var id: Int,
    var content: String,
    var summary: String,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)