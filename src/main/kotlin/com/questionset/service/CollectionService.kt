package com.questionset.service

import com.questionset.controller.params.CollectionCreateParam
import com.questionset.controller.params.CollectionUpdateParam
import com.questionset.dto.CollectionDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.model.Collection
import com.questionset.repository.CollectionRepository
import com.questionset.repository.QuestionRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class CollectionService {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var collectionRepository: CollectionRepository

    @Autowired
    lateinit var questionRepository: QuestionRepository

    fun listCollections(): List<CollectionDTO> {
        var collections = collectionRepository.findAll()
        return collections.map { it -> it.toDTO() }
    }

    fun getCollectionById(id: Int): CollectionDTO {
        var collection = collectionRepository.getById(id)
        return collection.toDTO()
    }

    fun createCollection(createParam: CollectionCreateParam): CollectionDTO {
        return createParam.let {
            var collection = Collection()
            collection.name = it.name
            collectionRepository.save(collection)
            collection.toDTO()
        }
    }

    fun updateCollection(id: Int, updateParam: CollectionUpdateParam): CollectionDTO {
        var collection = collectionRepository.findById(id)

        if(collection.isPresent) {
            var collectionEntity = collection.get()
            collectionEntity.let {
                it.name = updateParam.name
                collectionRepository.save(it)
            }
            return collectionEntity.toDTO()
        } else {
            throw ItemNotFoundException("Collection with id $id not exist")
        }
    }

    fun deleteCollection(id: Int) {
        collectionRepository.deleteById(id)
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun linkQuestionWithCollection(collectionId: Int, questionId: Int): CollectionDTO {
        var collection = collectionRepository.findById(collectionId)
        var question = questionRepository.findById(questionId)

        return if(collection.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                var collectionEntity = collection.get()
                questionEntity.collections.add(collectionEntity)
                questionRepository.save(questionEntity)
                entityManager.flush()
                collection = collectionRepository.findById(collectionId)
                collection.get().toDTO()
            } else {
                throw ItemNotFoundException("Question with id $questionId not exist.")
            }
        } else {
            throw ItemNotFoundException("Collection with id $collectionId not existing.")
        }

    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun detachQuestionWithCollection(collectionId: Int, questionId: Int): CollectionDTO {
        var collection = collectionRepository.findById(collectionId)
        var question = questionRepository.findById(questionId)
        return if(collection.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                questionEntity.collections.remove(collection.get())
                questionRepository.save(questionEntity)
                entityManager.flush()
                collection = collectionRepository.findById(collectionId)
                collection.get().toDTO()
            } else {
                throw ItemNotFoundException("Question with id $questionId not existing.")
            }
        } else {
            throw ItemNotFoundException("Collection with id $collectionId not existing.")
        }
    }

}