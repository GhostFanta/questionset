package com.questionset.controller

import com.questionset.controller.params.CollectionCreateParam
import com.questionset.controller.params.CollectionUpdateParam
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.service.CollectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CollectionController {
    companion object {
        const val collectionListRoute = "/collections"
        const val collectionRoute = "/collections/{collectionId}"
        const val collectionQuestionListRoute = "/collections/{collectionId}/questions"
        const val collectionQuestionRoute = "/collections/{collectionId}/questions/{questionId}"
    }

    @Autowired
    lateinit var collectionService: CollectionService

    @GetMapping(collectionListRoute)
    fun listCollections(): ResponseEntity<out Any> {
        try {
            return ResponseEntity.ok().body(collectionService.listCollections())
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping(collectionRoute)
    fun getCollectionById(@PathVariable("collectionId") id: Int): ResponseEntity<out Any>{
        try {
            return ResponseEntity.ok().body(collectionService.getCollectionById(id))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(collectionListRoute)
    fun createCollection(@RequestBody createParam: CollectionCreateParam): ResponseEntity<out Any>{
        try {
            return ResponseEntity.ok().body(collectionService.createCollection(createParam))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PutMapping(collectionRoute)
    fun updateCollection(@PathVariable("collectionId") id: Int, @RequestBody updateParam: CollectionUpdateParam): Any {
        try {
            return collectionService.updateCollection(id, updateParam)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping(collectionRoute)
    fun deleteCollection(@PathVariable("collectionId") id: Int): ResponseEntity<out Any> {
        try {
            collectionService.deleteCollection(id)
            return ResponseEntity.noContent().build()
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(collectionQuestionRoute)
    fun linkQuestionWithCollection(@PathVariable("collectionId") collectionId: Int, @PathVariable("questionId") questionId: Int): Any {
        try {
            return collectionService.linkQuestionWithCollection(collectionId, questionId)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping(collectionQuestionRoute)
    fun detachQuestionWithCollection(@PathVariable("collectionId") collectionId: Int, @PathVariable("questionId") questionId: Int ): Any {
        try {
            return collectionService.detachQuestionWithCollection(collectionId, questionId)
        } catch (e: ItemNotFoundException) {
            return ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            throw e
        }
    }
}