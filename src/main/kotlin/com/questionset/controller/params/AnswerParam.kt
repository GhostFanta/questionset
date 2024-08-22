package com.questionset.controller.params

data class AnswerCreateParam(
    var content: String,
    var selected: Boolean,
)

data class AnswerBatchCreateParam(
    var answers: List<AnswerCreateParam>
)

data class AnswerUpdateParam (
    var content: String,
    var selected: Boolean,
)