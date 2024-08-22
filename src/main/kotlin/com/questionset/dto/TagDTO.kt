package com.questionset.dto

import java.sql.Timestamp

data class TagDTO (
    var id:Int,
    var name: String,
    var questions: List<QuestionMemberDTO>?,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)

data class TagMemberDTO (
    var id:Int,
    var name: String,
    var createdAt: Timestamp,
    var updatedAt: Timestamp,
)