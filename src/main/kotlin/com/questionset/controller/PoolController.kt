package com.questionset.controller

import com.questionset.controller.params.PoolCreateParam
import com.questionset.controller.params.PoolUpdateParam
import com.questionset.controller.params.QuestionCreateParam
import com.questionset.dto.PoolDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.service.PoolService
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint
import org.springframework.web.bind.annotation.*

@RestController
class PoolController {
    companion object {
        const val poolListRoute = "/pools"
        const val poolRoute = "/pools/{poolId}"
        const val poolQuestionListRoute = "/pools/{poolId}/questions"
        const val poolQuestionRoute = "/pools/{poolId}/questions/{questionId}"
    }

    @Autowired
    lateinit var poolService: PoolService

    @GetMapping(poolListRoute)
    fun listPools(): ResponseEntity<List<PoolDTO>> {
        try {
            return ResponseEntity.ok().body(poolService.listPools())
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping(poolRoute)
    fun getPoolById(@PathVariable("poolId") id: Int): ResponseEntity<PoolDTO> {
        try {
            return ResponseEntity.ok().body(poolService.getPoolById(id))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(poolListRoute)
    fun createPool(@RequestBody createParam: PoolCreateParam): ResponseEntity<PoolDTO> {
        try {
            return ResponseEntity.ok().body(poolService.createPool(createParam))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PutMapping(poolRoute)
    fun updatePool(@PathVariable("poolId") id: Int, @RequestBody updateParam: PoolUpdateParam): ResponseEntity<PoolDTO> {
        try {
            return ResponseEntity.ok().body(poolService.updatePool(id, updateParam))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping(poolRoute)
    fun deletePool(@PathVariable("poolId") id:Int): ResponseEntity<out Any> {
        return try {
            poolService.deletePool(id)
            return ResponseEntity.noContent().build()
        } catch (e: ItemNotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(poolQuestionListRoute)
    fun createQuestionForPool(@PathVariable("poolId") poolId: Int, @RequestBody createParam: QuestionCreateParam): ResponseEntity<out Any> {
        try {
            return ResponseEntity(poolService.createQuestionForPool(poolId, createParam), HttpStatus.CREATED)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(poolQuestionRoute)
    fun linkQuestionWithPool(@PathVariable("poolId") poolId: Int, @PathVariable("questionId") questionId: Int): ResponseEntity<PoolDTO> {
        try {
            return ResponseEntity(poolService.linkQuestionWithPool(poolId, questionId), HttpStatus.CREATED)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping(poolQuestionRoute)
    fun detachQuestionWithPool(@PathVariable("poolId") poolId: Int, @PathVariable("questionId") questionId: Int): ResponseEntity<PoolDTO> {
        try {
            return ResponseEntity(poolService.detachQuestionFromPool(poolId, questionId), HttpStatus.NO_CONTENT)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }
}