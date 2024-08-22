package com.questionset.service

import com.questionset.controller.params.AnswerUpdateParam
import com.questionset.dto.AnswerDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.repository.AnswerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnswerSerivce {
    @Autowired
    lateinit var answerRepository: AnswerRepository

    fun listAnswers(): List<AnswerDTO> {
        return mutableListOf()
    }

    fun updateAnswer(id: Int, updateParam: AnswerUpdateParam): AnswerDTO {
        var answer = answerRepository.findById(id)
        if(answer.isPresent) {
            var answerEntity = answer.get()
            answerEntity.let {
                it.content = updateParam.content
                answerRepository.save(it)
            }

            return answerEntity.toDTO()
        } else {
            throw ItemNotFoundException("Answer with id ${id} does not exist.")
        }
    }

    fun deleteAnswer(id: Int) {
        var answer = answerRepository.findById(id)
        if(answer.isPresent) {
            answerRepository.deleteById(id)
        } else {
            throw ItemNotFoundException("Answer with id ${id} does not exist")
        }
    }

}