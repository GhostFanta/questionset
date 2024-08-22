package com.questionset.bootstrap

import com.questionset.model.Collection
import com.questionset.model.Question
import com.questionset.model.QuestionStatus
import com.questionset.model.Tag
import com.questionset.repository.CollectionRepository
import com.questionset.repository.QuestionRepository
import com.questionset.repository.QuestionStatusRepository
import com.questionset.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer: CommandLineRunner {

    @Autowired
    lateinit var tagRepository: TagRepository

    @Autowired
    lateinit var questionRepository: QuestionRepository

    @Autowired
    lateinit var questionStatusRepository: QuestionStatusRepository

    @Autowired
    lateinit var collectionRepository: CollectionRepository


    override fun run(vararg args: String?) {

        var questionStatus1 = QuestionStatus()
        questionStatus1.name = "pending"
        questionStatus1 = questionStatusRepository.findFirstByName("pending").orElse(questionStatusRepository.save(questionStatus1))

        var questionStatus2 = QuestionStatus()
        questionStatus2.name = "done"
        questionStatus2 = questionStatusRepository.findFirstByName("done").orElse(questionStatusRepository.save(questionStatus2))

        var question = Question()
        question.content = "question1"
        question.tags = mutableSetOf<Tag>()
        question.status  = questionStatus1
        question = questionRepository.save(question)

        var question2 = Question()
        question.content = "question2"
        question2.tags = mutableSetOf()
        question2.status = questionStatus2
        question2 = questionRepository.save(question2)

        var tag1 = Tag()
        tag1.name = "tag1"
        tag1.questions.plus(question)

        tag1 = tagRepository.save(tag1)

        var tag2 = Tag()
        tag2.name = "tag2"
        tag2 = tagRepository.save(tag2)

        tagRepository.findAll().forEach{tag -> {
            println(tag.name)
        }}

        var collection = Collection()
        collection.name = "collection1"

        collection = collectionRepository.save(collection)
        collection.questions.add(question)
        collection.questions.add(question2)
        collectionRepository.save(collection)

    }
}