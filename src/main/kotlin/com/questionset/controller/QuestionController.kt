package com.questionset.controller

import com.questionset.controller.params.AnswerBatchCreateParam
import com.questionset.controller.params.AnswerCreateParam
import com.questionset.controller.params.QuestionCreateParam
import com.questionset.controller.params.QuestionUpdateParam
import com.questionset.dto.QuestionDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus

@RestController
class QuestionController {
    companion object {
        const val questionsListRoute = "/questions"
        const val questionRoute = "/questions/{questionId}"
        const val questionAnswerListRoute = "/questions/{questionId}/answers"
        const val questionAnswerBatchListRoute = "/questions/{questionId}/answers/batch"
        const val questionAnswerRoute = "/questions/{questionId}/answers/{answerId}"
    }

    @Autowired
    lateinit var questionService: QuestionService

    @GetMapping(questionsListRoute)
    fun listQuestions(): List<QuestionDTO> {
        return questionService.listQuestions()
    }

    @GetMapping(questionRoute)
    fun getQuestionById(@PathVariable("questionId") id: Int): ResponseEntity<out Any> {
        try {
            return ResponseEntity.ok().body(questionService.getQuestionById(id))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @PostMapping(questionsListRoute)
    fun createQuestion(@RequestBody createParam: QuestionCreateParam): QuestionDTO {
        return questionService.createQuestion(createParam)
    }

    @PutMapping(questionRoute)
    fun updateQuestion(@PathVariable("questionId") id: Int, @RequestBody updateParam: QuestionUpdateParam): ResponseEntity<out Any> {
        try {
            return ResponseEntity.ok().body(questionService.updateQuestion(id, updateParam))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @DeleteMapping(questionRoute)
    fun deleteQuestion(@PathVariable("questionId") id: Int): ResponseEntity<String> {
        var responseHeader:HttpHeaders = HttpHeaders();
        try{
            questionService.deleteQuestion(id)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            return ResponseEntity.noContent().build()
        }
    }

    @PostMapping(questionAnswerListRoute) fun createAnswerForQuestion(@PathVariable("questionId") questionId:Int, @RequestBody createParam: AnswerCreateParam): ResponseEntity<out Any>{
        try {

            return ResponseEntity(questionService.createAnswerForQuestion(questionId, createParam), HttpStatus.CREATED)
        } catch(e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch(e: Exception) {
            throw e
        }
    }

    @PostMapping(questionAnswerBatchListRoute)
    fun createAnswersForQuestion(@PathVariable("questionId") questionId: Int, @RequestBody createParam: AnswerBatchCreateParam): ResponseEntity<out Any>{
        try {
            return ResponseEntity(questionService.createBatchAnswerForQuestion(questionId, createParam), HttpStatus.CREATED)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

}