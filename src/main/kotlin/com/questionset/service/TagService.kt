package com.questionset.service

import com.questionset.controller.params.TagCreateParam
import com.questionset.controller.params.TagUpdateParam
import com.questionset.dto.TagDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.model.Tag
import com.questionset.repository.QuestionRepository
import com.questionset.repository.TagRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation

@Service
class TagService {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var tagRepository: TagRepository

    @Autowired
    lateinit var questionRepository: QuestionRepository

    fun listTags(): List<TagDTO> {
        val tags = tagRepository.findAll()
        return tags.map { it -> it.toDTO() }
    }

    fun getTagById(id: Int): TagDTO {
        val tag = tagRepository.findById(id)
        if(tag.isPresent) {
            return tag.get().toDTO()
        } else {
            throw ItemNotFoundException("Tag with id $id not existing.")
        }
    }

    fun createTag(createParam: TagCreateParam): TagDTO {
        return createParam.let {
            var tag = Tag()
            tag.name = it.name
            tag = tagRepository.save(tag)
            tag.toDTO()
        }
    }

    fun updateTag(id: Int, updateParam: TagUpdateParam): TagDTO {
        var tag = tagRepository.findById(id)
        return updateParam.let {
            var tagEntity = tag.get()
            tagEntity.name = updateParam.name
            tagRepository.save(tagEntity)
            tagEntity.toDTO()
        }
    }

    fun deleteTag(id: Int) {
        tagRepository.deleteById(id)
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun linkQuestionWithTag(tagId: Int, questionId: Int): TagDTO {
        var tag = tagRepository.findById(tagId)
        var question = questionRepository.findById(questionId)

        return if(tag.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                var tagEntity = tag.get()
                questionEntity.tags.add(tagEntity)
                questionRepository.save(questionEntity)
                entityManager.flush()
                tag = tagRepository.findById(tagId)
                tag.get().toDTO()
            } else {
                throw ItemNotFoundException("Question with id $questionId not exist.")
            }
        } else {
            throw ItemNotFoundException("Tag with id $tagId not existing")
        }
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun detachQuestionWithTag(tagId: Int, questionId: Int): TagDTO {
        var tag = tagRepository.findById(tagId)
        var question = questionRepository.findById(questionId)

        return if(tag.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                questionEntity.tags.remove(tag.get())
                questionRepository.save(questionEntity)
                entityManager.flush()
                tag = tagRepository.findById(tagId)
                tag.get().toDTO()
            } else {
                throw ItemNotFoundException("Question with id $questionId not exist.")
            }
        } else {
            throw ItemNotFoundException("Tag with id $tagId not existing.")
        }
    }
}