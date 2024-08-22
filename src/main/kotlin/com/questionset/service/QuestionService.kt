package com.questionset.service

import com.questionset.controller.params.AnswerBatchCreateParam
import com.questionset.controller.params.AnswerCreateParam
import com.questionset.controller.params.QuestionCreateParam
import com.questionset.controller.params.QuestionUpdateParam
import com.questionset.dto.QuestionDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.model.Answer
import com.questionset.model.Question
import com.questionset.model.QuestionStatus
import com.questionset.repository.AnswerRepository
import com.questionset.repository.QuestionRepository
import com.questionset.repository.QuestionStatusRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
class QuestionService {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var questionRepository: QuestionRepository

    @Autowired
    lateinit var answerRepository: AnswerRepository

    @Autowired
    lateinit var questionStatusRepository: QuestionStatusRepository

    fun listQuestions(): List<QuestionDTO> {
        var questions = questionRepository.findAll()
        return questions.map{
            it.toDTO()
        }
    }

    fun getQuestionById(id: Int): QuestionDTO {
        var question = questionRepository.findById(id)
        if(question.isPresent) {
            val questionEntity = question.get()
            return questionEntity.toDTO()
        } else {
            throw ItemNotFoundException("Question with ID $id does not exist.")
        }
    }

    fun createQuestion(createParam: QuestionCreateParam): QuestionDTO {
        var questionEntity = createParam.let {
            var question = Question()
            question.content = it.content
            question.summary = it.summary.toString()
            question.status = questionStatusRepository
                .findFirstByName(it.status.toString())
                .orElse(questionStatusRepository.save(QuestionStatus(name = "pending")))
            question = questionRepository.save(question)
            question
        }
        return questionEntity.toDTO()
    }

    fun updateQuestion(id: Int, updateParam: QuestionUpdateParam): QuestionDTO {
        var question = questionRepository.findById(id)
        if(question.isPresent) {
            var questionEntity = question.get()
            questionEntity.let {
                it.content = updateParam.content
                it.summary = updateParam.summary.toString()
                it.status = questionStatusRepository
                    .findFirstByName(updateParam.status.toString())
                    .orElse(it.status)
                questionRepository.save(it)
            }
            return questionEntity.toDTO()
        } else {
            throw ItemNotFoundException("Question with ID $id does not exist.")
        }
    }

    fun deleteQuestion(id: Int) {
        var question = questionRepository.findById(id)
        if(question.isPresent) {
            questionRepository.deleteById(id)
        } else {
            throw ItemNotFoundException("Question with ID $id does not exist.")
        }
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun createAnswerForQuestion(questionId: Int, createParam: AnswerCreateParam): QuestionDTO {
        var question = questionRepository.findById(questionId)
        if(question.isPresent) {
            var questionEntity = question.get()
            createParam.let {
                var answer = Answer()
                answer.question = questionEntity
                answer.content = it.content
                answer.selected = it.selected
                answerRepository.save(answer)
                entityManager.flush()
            }
        }
        question = questionRepository.findById(questionId)
        return question.get().toDTO()
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun createBatchAnswerForQuestion(questionId: Int, createParam: AnswerBatchCreateParam): QuestionDTO {
        var question = questionRepository.findById(questionId)
        if(question.isPresent) {
            var questionEntity = question.get()
            createParam.let {
                it.answers.forEach {
                    ans -> {
                        var answer = Answer()
                        answer.content = ans.content
                        answer.selected = ans.selected
                        answerRepository.save(answer)
                        questionEntity.answers.add(answer)
                    }
                }
                questionEntity = questionRepository.save(questionEntity)
                entityManager.flush()
            }
            return questionEntity.toDTO()
        }
        else {
            throw ItemNotFoundException("Question with Id $questionId does not exist")
        }
    }

}
