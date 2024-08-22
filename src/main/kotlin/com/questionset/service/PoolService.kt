package com.questionset.service

import com.questionset.controller.params.PoolCreateParam
import com.questionset.controller.params.PoolUpdateParam
import com.questionset.controller.params.QuestionCreateParam
import com.questionset.dto.PoolDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.model.Pool
import com.questionset.model.Question
import com.questionset.model.QuestionStatus
import com.questionset.repository.PoolRepository
import com.questionset.repository.QuestionRepository
import com.questionset.repository.QuestionStatusRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hibernate.boot.model.source.internal.hbm.CompositeIdentifierSingularAttributeSourceManyToOneImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
class PoolService {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var poolRepository: PoolRepository

    @Autowired
    lateinit var questionRepository: QuestionRepository

    @Autowired
    lateinit var questionStatusRepository: QuestionStatusRepository

    fun listPools(): List<PoolDTO> {
        var pools = poolRepository.findAll()
        return pools.map { pool -> pool.toDTO() }
    }

    fun getPoolById(id: Int): PoolDTO {
        var pool = poolRepository.findById(id)
        if(pool.isPresent) {
            val poolEntity = pool.get()
            return poolEntity.toDTO()
        } else {
            throw ItemNotFoundException("Pool with id $id not exist")
        }
    }

    fun createPool(createParam: PoolCreateParam): PoolDTO {
        return createParam.let {
            var pool = Pool()
            pool.name = createParam.name
            pool.summary = createParam.summary
            pool = poolRepository.save(pool)
            pool.toDTO()
        }
    }

    fun updatePool(id: Int, updateParam: PoolUpdateParam): PoolDTO {
        var pool = poolRepository.findById(id)
        if(pool.isPresent) {
            var poolEntity = pool.get()
            poolEntity.let {
                it.name = updateParam.name
                it.summary = updateParam.summary
                poolRepository.save(it)
            }
            return poolEntity.toDTO()
        } else {
            throw ItemNotFoundException("Pool with id $id not exist")
        }
    }

    fun deletePool(id: Int) {
        poolRepository.deleteById(id)
    }


    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun createQuestionForPool(poolId: Int, createParam: QuestionCreateParam) {
        var pool = poolRepository.findById(poolId)
        if(pool.isPresent) {
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

            var poolEntity = pool.get()
            poolEntity.questions.add(questionEntity)
            poolEntity = poolRepository.save(poolEntity)
            entityManager.flush()
            poolEntity.toDTO()

        } else {
            throw ItemNotFoundException("Pool with ID $poolId not exist")
        }

    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun linkQuestionWithPool(poolId: Int, questionId: Int): PoolDTO {
        var question = questionRepository.findById(questionId)
        var pool = poolRepository.findById(poolId)

        if(pool.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                var poolEntity = pool.get()
                poolEntity.questions.add(questionEntity)
                poolEntity = poolRepository.save(poolEntity)
                entityManager.flush()
                return poolEntity.toDTO()
            } else {
                throw ItemNotFoundException("Question with ID $questionId not exist")
            }
        } else {
            throw ItemNotFoundException("Pool with ID $poolId not exist.")
        }
    }

    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun detachQuestionFromPool(poolId: Int, questionId: Int): PoolDTO {
        var question = questionRepository.findById(questionId)
        var pool = poolRepository.findById(poolId)

        if(pool.isPresent) {
            if(question.isPresent) {
                var questionEntity = question.get()
                var poolEntity = pool.get()
                poolEntity.questions.remove(questionEntity)
                poolEntity = poolRepository.save(poolEntity)
                entityManager.flush()
                return poolEntity.toDTO()
            } else {
                throw ItemNotFoundException("Question with ID $questionId not exist")
            }
        } else {
            throw ItemNotFoundException("Pool with ID $poolId not exist.")
        }
    }
}