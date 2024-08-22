package com.questionset.controller

import com.questionset.controller.params.TagCreateParam
import com.questionset.controller.params.TagUpdateParam
import com.questionset.dto.TagDTO
import com.questionset.exceptions.ItemNotFoundException
import com.questionset.service.TagService
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
class TagController {
    companion object {
        const val tagListRoute = "/tags"
        const val tagRoute = "/tags/{tagId}"
        const val tagQuestionRoute = "/tags/{tagId}/questions/{questionId}"
    }

    @Autowired
    lateinit var tagService: TagService

    @GetMapping(tagListRoute)
    fun listTags(): List<TagDTO> {
        return tagService.listTags()
    }

    @GetMapping(tagRoute)
    fun getTagById(@PathVariable("tagId") id: Int): ResponseEntity<out Any>  {
        try {
            return ResponseEntity.ok().body(tagService.getTagById(id))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @PostMapping(tagListRoute)
    fun createTag(@RequestBody createParam: TagCreateParam): ResponseEntity<out Any>{
        try {
            return ResponseEntity<TagDTO>(tagService.createTag(createParam), HttpStatus.CREATED)
        } catch (e: Exception) {
            throw e
        }
    }

    @PutMapping(tagRoute)
    fun updateTag(@PathVariable("tagId") id: Int, @RequestBody updateParam: TagUpdateParam): ResponseEntity<out Any>{
        try {
            return ResponseEntity.ok().body(tagService.updateTag(id, updateParam))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping(tagRoute)
    fun deleteTag(@PathVariable("tagId") id:Int): ResponseEntity<out Any> {
        try {
            tagService.deleteTag(id)
            return ResponseEntity.noContent().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping(tagQuestionRoute)
    fun linkQuestionWithTag(@PathVariable("tagId") tagId: Int, @PathVariable("questionId") questionId: Int): ResponseEntity<out Any>{
        try {
            return ResponseEntity.ok().body(tagService.linkQuestionWithTag(tagId, questionId))
        } catch (e: ItemNotFoundException) {
            return ResponseEntity.notFound().build()
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping(tagQuestionRoute)
    fun detachQuestionWithTag(@PathVariable("tagId") tagId: Int, @PathVariable("questionId") questionId: Int): ResponseEntity<out Any>{
        try {
            return ResponseEntity<TagDTO>(tagService.detachQuestionWithTag(tagId, questionId), HttpStatus.NO_CONTENT)
        } catch(e: Exception) {
            throw e
        }
    }
}