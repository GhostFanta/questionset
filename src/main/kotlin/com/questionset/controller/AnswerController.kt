package com.questionset.controller

import com.questionset.controller.params.AnswerUpdateParam
import com.questionset.dto.AnswerDTO
import com.questionset.service.AnswerSerivce
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AnswerController {

    companion object {
        const val answerListRoute = "/answers"
        const val answerRoute = "/answers/{answerId}"
    }

    @Autowired
    lateinit var answerSerivce: AnswerSerivce

    @GetMapping(answerListRoute)
    fun listAnswers(): List<AnswerDTO> {
        return answerSerivce.listAnswers()
    }

    @PutMapping(answerRoute)
    fun updateAnswer(@PathVariable("answerId") id: Int, @RequestBody updateParam: AnswerUpdateParam) : AnswerDTO {
        return  answerSerivce.updateAnswer(id, updateParam)
    }

    @DeleteMapping(answerRoute)
    fun deleteAnswer(@PathVariable("answerId") id: Int) {
        return answerSerivce.deleteAnswer(id)
    }
}