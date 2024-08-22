package com.questionset.controller.params

data class QuestionCreateParam (
    var id: Int,
    var content: String,
    var summary: String?,
    var status: String?,
    var answers: List<AnswerCreateParam>?,
)

data class QuestionUpdateParam (
    var id: Int,
    var content: String,
    var summary: String?,
    var status: String?,
)